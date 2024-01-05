package com.kto.employejetnexa.ui.employe

import com.kto.employejetnexa.domain.usecase.employe.DeleteEmploye
import com.kto.employejetnexa.domain.usecase.employe.GetEmploye
import com.kto.employejetnexa.motherobject.EmployeMotherObject
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EmployeViewModelTest {

    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: EmployeViewModel

    @MockK
    private lateinit var getEmploye: GetEmploye

    @MockK
    private lateinit var deleteEmploye: DeleteEmploye

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `when response is not emptyList getEmployes should val result return state list of employeModel`() {
        viewModel = EmployeViewModel(getEmploye, deleteEmploye)
        runTest {
            coEvery { getEmploye() } returns listOf(EmployeMotherObject.anyResponse.toDomain())
            viewModel.getEmployes()
        }
        runBlocking {
            coVerify { getEmploye() }
            assertTrue(viewModel.stateList.value.isNotEmpty())
            assertEquals(
                viewModel.stateList.value,
                listOf(EmployeMotherObject.anyResponse.toDomain())
            )
        }
    }

    @Test
    fun `when response is emptyList getEmployes should val result return state of emptyList`() {
        viewModel = EmployeViewModel(getEmploye, deleteEmploye)
        coEvery { getEmploye() } returns null
        viewModel.getEmployes()
        runBlocking {
            coVerify { getEmploye() }
            assertTrue(viewModel.stateList.value.isEmpty())
        }
    }

    @Test
    fun `when response is corret deleteEmployeId should return state true`() {
        viewModel = EmployeViewModel(getEmploye, deleteEmploye)
        val id = "1"
        coEvery { deleteEmploye(id) } returns "Delete Correct"
        viewModel.deleteEmployeId(id)
        runBlocking {
            coVerify { deleteEmploye(id) }
            assertEquals(
                viewModel.stateDelete.value,
                true
            )
        }
    }
}