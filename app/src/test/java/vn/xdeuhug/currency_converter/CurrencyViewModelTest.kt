package vn.xdeuhug.currency_converter


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import vn.xdeuhug.currency_converter.domain.usecase.ConvertCurrencyUseCase
import vn.xdeuhug.currency_converter.presentation.viewmodel.CurrencyViewModel
import java.math.BigDecimal
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 27 / 10 / 2024
 */

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CurrencyViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CurrencyViewModel

    @Mock
    private lateinit var mockConvertCurrencyUseCase: ConvertCurrencyUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = CurrencyViewModel(mockConvertCurrencyUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `setAmount updates LiveData`() = runTest {
        val testAmount = BigDecimal("100.00")
        viewModel.amount.observeForever { }
        viewModel.setAmount(testAmount)
        assertEquals(testAmount, viewModel.amount.value)
    }

    @Test
    fun `convertCurrency updates convertedAmount LiveData on success`() = runTest {
        val amount = BigDecimal("100.00")
        val from = "USD"
        val to = "VND"
        val expectedResult = BigDecimal("2300000.00")
        whenever(mockConvertCurrencyUseCase(amount, from, to)).thenReturn(expectedResult)

        viewModel.convertCurrency(amount, from, to)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(expectedResult, viewModel.convertedAmount.value)
        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun `convertCurrency sets error message on failure`() = runTest {
        val amount = BigDecimal("100.00")
        val from = "USD"
        val to = "VND"

        whenever(mockConvertCurrencyUseCase(amount, from, to)).thenThrow(RuntimeException("Conversion error"))

        viewModel.convertCurrency(amount, from, to)
        testDispatcher.scheduler.advanceUntilIdle()

        assertNotNull(viewModel.errorMessage.value)
        assertEquals("Conversion failed: Conversion error", viewModel.errorMessage.value)
    }

    @Test
    fun `onAmountChange sets error message for invalid amount`() = runTest {
        viewModel.onAmountChange("0")
        assertNotNull(viewModel.errorMessage.value)
        assertEquals("Amount must be greater than 0", viewModel.errorMessage.value)
    }

    @Test
    fun `onAmountChange sets amount for valid input`() = runTest {
        viewModel.onAmountChange("100")
        assertEquals(BigDecimal("100"), viewModel.amount.value)
        assertNull(viewModel.errorMessage.value)
    }
}

