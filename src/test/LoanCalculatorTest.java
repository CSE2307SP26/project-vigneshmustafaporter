package test;


import main.LoanCalculator;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class LoanCalculatorTest {
  
   @Test
   public void testCalculateMonthlyPayment() {
       double payment = LoanCalculator.calculateMonthlyPayment(10000, 5, 10);
       assertEquals(106.07, payment, 0.01);
   }


   @Test
   public void testCalculateTotalPayment() {
       double totalPayment = LoanCalculator.calculateTotalPayment(10000, 5, 10);
       assertEquals(12728.49, totalPayment, 0.01);
   }


   @Test
   public void testCalculateTotalInterest() {
       double totalInterest = LoanCalculator.calculateTotalInterest(10000, 5, 10);
       assertEquals(2728.49, totalInterest, 0.01);
   }


   @Test
   public void testValidateLoanParametersValid() {
       assertTrue(LoanCalculator.validateLoanParameters(10000, 5, 10));
   }


   @Test
   public void testValidateLoanParametersInvalid() {
       assertFalse(LoanCalculator.validateLoanParameters(-10000, 5, 10));
       assertFalse(LoanCalculator.validateLoanParameters(10000, -5, 10));
       assertFalse(LoanCalculator.validateLoanParameters(10000, 5, -10));
       assertFalse(LoanCalculator.validateLoanParameters(0, 5, 10));
   }


   @Test
   public void testCalculateMonthlyPaymentZeroInterest() {
       double payment = LoanCalculator.calculateMonthlyPayment(12000, 0, 5);
       assertEquals(200, payment, 0.01);
   }


   @Test
   public void testCalculateMonthlyPaymentThrowsException() {
       assertThrows(IllegalArgumentException.class, () -> {
           LoanCalculator.calculateMonthlyPayment(-5000, 5, 10);
       });
   }


   @Test
   public void testTotalInterestWithZeroRate() {
       double totalInterest = LoanCalculator.calculateTotalInterest(10000, 0, 5);
       assertEquals(0, totalInterest, 0.01);
   }
}
