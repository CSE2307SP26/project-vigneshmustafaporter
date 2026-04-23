package test;


import main.SavingsGoalCalculator;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class SavingsGoalCalculatorTest {


   @Test
   public void testCalculateMonthsToGoalTypical() {
       int months = SavingsGoalCalculator.calculateMonthsToGoal(1000, 100, 5, 5000);
       assertEquals(36, months);
   }


   @Test
   public void testCalculateMonthsToGoalZeroInterest() {
       int months = SavingsGoalCalculator.calculateMonthsToGoal(1000, 100, 0, 2200);
       assertEquals(12, months);
   }


   @Test
   public void testCalculateFutureValueTypical() {
       double futureValue = SavingsGoalCalculator.calculateFutureValue(1000, 100, 5, 12);
       assertEquals(2315.11, futureValue, 0.05);
   }


   @Test
   public void testCalculateFutureValueZeroInterest() {
       double futureValue = SavingsGoalCalculator.calculateFutureValue(1000, 100, 0, 12);
       assertEquals(2200, futureValue, 0.01);
   }


   @Test
   public void testCalculateMonthsToGoalInvalidParameters() {
       assertThrows(IllegalArgumentException.class, () -> {
           SavingsGoalCalculator.calculateMonthsToGoal(-100, 100, 5, 5000);
       });
       assertThrows(IllegalArgumentException.class, () -> {
           SavingsGoalCalculator.calculateMonthsToGoal(1000, 0, 5, 5000);
       });
       assertThrows(IllegalArgumentException.class, () -> {
           SavingsGoalCalculator.calculateMonthsToGoal(1000, 100, -1, 5000);
       });
       assertThrows(IllegalArgumentException.class, () -> {
           SavingsGoalCalculator.calculateMonthsToGoal(1000, 100, 5, 1000);
       });
   }


   @Test
   public void testCalculateFutureValueInvalidParameters() {
       assertThrows(IllegalArgumentException.class, () -> {
           SavingsGoalCalculator.calculateFutureValue(-1000, 100, 5, 12);
       });
       assertThrows(IllegalArgumentException.class, () -> {
           SavingsGoalCalculator.calculateFutureValue(1000, -100, 5, 12);
       });
       assertThrows(IllegalArgumentException.class, () -> {
           SavingsGoalCalculator.calculateFutureValue(1000, 100, -5, 12);
       });
       assertThrows(IllegalArgumentException.class, () -> {
           SavingsGoalCalculator.calculateFutureValue(1000, 100, 5, -12);
       });
   }


   @Test
   public void testCalculateMonthsToGoalUnreachableWithinLimit() {
       assertThrows(IllegalArgumentException.class, () -> {
           SavingsGoalCalculator.calculateMonthsToGoal(0, 1, 0, 2001);
       });
   }
}
