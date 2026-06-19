package com.example.springbootmvcexample.service;

import com.example.springbootmvcexample.dto.ExpenseSummaryDTO;
import com.example.springbootmvcexample.model.ExpenseTracker;
import com.example.springbootmvcexample.model.User;
import com.example.springbootmvcexample.repository.ExpenseTrackerRepository;
import com.example.springbootmvcexample.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseTrackerServiceTest {

    @Mock
    private ExpenseTrackerRepository expenseRepo;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ExpenseTrackerService expenseService;

    private User testUser;

    @BeforeEach
    void setUp() {
        // create a fake user that will be returned by the mocked userRepository
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@test.com");
        testUser.setPassword("password");
        testUser.setName("Test User");

        // mock Spring Security context so getCurrentUserEmail() returns our test email
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn("test@test.com");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getExpenseSummary_returnsCorrectTotalSpent() {
        // arrange - set up fake expenses the repository will return
        ExpenseTracker expense1 = new ExpenseTracker();
        expense1.setId(1L);
        expense1.setUserId(1L);
        expense1.setAmount(100.0);
        expense1.setCategory("Food");
        expense1.setPaymentMethod("Cash");
        expense1.setDate(LocalDate.of(2026, 6, 1));

        ExpenseTracker expense2 = new ExpenseTracker();
        expense2.setId(2L);
        expense2.setUserId(1L);
        expense2.setAmount(50.0);
        expense2.setCategory("Transport");
        expense2.setPaymentMethod("Credit card");
        expense2.setDate(LocalDate.of(2026, 6, 15));

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(testUser));
        when(expenseRepo.findByUserIdAndDateBetween(1L,
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2026, 6, 30)))
                .thenReturn(List.of(expense1, expense2));

        // act
        ExpenseSummaryDTO result = expenseService.getExpenseSummary(
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2026, 6, 30),
                null,
                null);

        // assert
        assertThat(result.getTotalSpent()).isEqualTo(150.0);
        assertThat(result.getByCategory()).containsEntry("Food", 100.0);
        assertThat(result.getByCategory()).containsEntry("Transport", 50.0);
        assertThat(result.getByPaymentMethod()).containsEntry("Cash", 100.0);
        assertThat(result.getByPaymentMethod()).containsEntry("Credit card", 50.0);
    }

    @Test
    void getExpenseSummary_withCategoryFilter_returnsOnlyMatchingCategory() {
        // arrange
        ExpenseTracker foodExpense = new ExpenseTracker();
        foodExpense.setId(1L);
        foodExpense.setUserId(1L);
        foodExpense.setAmount(100.0);
        foodExpense.setCategory("Food");
        foodExpense.setPaymentMethod("Cash");
        foodExpense.setDate(LocalDate.of(2026, 6, 1));

        ExpenseTracker transportExpense = new ExpenseTracker();
        transportExpense.setId(2L);
        transportExpense.setUserId(1L);
        transportExpense.setAmount(50.0);
        transportExpense.setCategory("Transport");
        transportExpense.setPaymentMethod("Credit card");
        transportExpense.setDate(LocalDate.of(2026, 6, 15));

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(testUser));
        when(expenseRepo.findByUserIdAndDateBetween(1L,
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2026, 6, 30)))
                .thenReturn(List.of(foodExpense, transportExpense));

        // act - filter by Food only
        ExpenseSummaryDTO result = expenseService.getExpenseSummary(
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2026, 6, 30),
                "Food",
                null);

        // assert - only Food expense should be counted
        assertThat(result.getTotalSpent()).isEqualTo(100.0);
        assertThat(result.getByCategory()).containsOnlyKeys("Food");
        assertThat(result.getByCategory()).doesNotContainKey("Transport");
    }

    @Test
    void getExpenseSummary_withNoExpenses_returnsZeroTotals() {
        // arrange
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(testUser));
        when(expenseRepo.findByUserIdAndDateBetween(1L,
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2026, 6, 30)))
                .thenReturn(List.of());

        // act
        ExpenseSummaryDTO result = expenseService.getExpenseSummary(
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2026, 6, 30),
                null,
                null);

        // assert
        assertThat(result.getTotalSpent()).isEqualTo(0.0);
        assertThat(result.getByCategory()).isEmpty();
        assertThat(result.getByPaymentMethod()).isEmpty();
    }
}
