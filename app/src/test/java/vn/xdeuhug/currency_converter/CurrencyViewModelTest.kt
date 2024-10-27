package vn.xdeuhug.currency_converter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import vn.xdeuhug.currency_converter.domain.usecase.ConvertCurrencyUseCase
import vn.xdeuhug.currency_converter.presentation.viewmodel.CurrencyViewModel
import java.math.BigDecimal
/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 27 / 10 / 2024
 */
class CurrencyViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CurrencyViewModel

    @Mock
    private lateinit var mockConvertCurrencyUseCase: ConvertCurrencyUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = CurrencyViewModel(mockConvertCurrencyUseCase)
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `setAmount updates LiveData`() {
        val testAmount = BigDecimal("100.00")
        viewModel.amount.observeForever { }
        viewModel.setAmount(testAmount)
        assertEquals(testAmount, viewModel.amount.value)
    }

    @Test
    fun `convertCurrency updates convertedAmount LiveData on success`() = runBlocking {
        val amount = BigDecimal("100.00")
        val from = "USD"
        val to = "VND"
        val expectedResult = BigDecimal("2300000.00")
        whenever(mockConvertCurrencyUseCase(amount, from, to)).thenReturn(expectedResult)

        viewModel.convertCurrency(amount, from, to)

        assertEquals(expectedResult, viewModel.convertedAmount.value)
        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun `convertCurrency sets error message on failure`() = runBlocking {
        val amount = BigDecimal("100.00")
        val from = "USD"
        val to = "VND"

        whenever(mockConvertCurrencyUseCase(amount, from, to)).thenThrow(RuntimeException("Conversion error"))

        viewModel.convertCurrency(amount, from, to)

        assertNotNull(viewModel.errorMessage.value)
        assertEquals("Conversion failed: Conversion error", viewModel.errorMessage.value)
    }

    @Test
    fun `onAmountChange sets error message for invalid amount`() {
        viewModel.onAmountChange("0")
        assertNotNull(viewModel.errorMessage.value)
        assertEquals("Amount must be greater than 0", viewModel.errorMessage.value)
    }

    @Test
    fun `onAmountChange sets amount for valid input`() {
        viewModel.onAmountChange("100")
        assertEquals(BigDecimal("100"), viewModel.amount.value)
        assertNull(viewModel.errorMessage.value)
    }
}
