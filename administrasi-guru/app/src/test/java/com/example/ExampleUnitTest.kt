package com.example

import com.example.data.CurriculumBank
import com.example.model.StudentGrade
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testCurriculumBankSubjects() {
    assertTrue(CurriculumBank.SUBJECTS.containsKey("Matematika"))
    assertTrue(CurriculumBank.SUBJECTS.containsKey("Informatika"))
    assertTrue(CurriculumBank.SUBJECTS.containsKey("Bahasa Indonesia"))
    assertTrue(CurriculumBank.SUBJECTS.containsKey("IPA"))
    assertTrue(CurriculumBank.SUBJECTS.containsKey("IPS"))
    assertTrue(CurriculumBank.SUBJECTS.containsKey("Bahasa Inggris"))
    assertTrue(CurriculumBank.SUBJECTS.containsKey("PPKn"))
    assertTrue(CurriculumBank.SUBJECTS.containsKey("Seni Budaya"))
    assertTrue(CurriculumBank.SUBJECTS.containsKey("B. Indramayu"))
    assertTrue(CurriculumBank.SUBJECTS.containsKey("PJOK"))
  }

  @Test
  fun testRombelAdaptation() {
    val tps = CurriculumBank.SUBJECTS["Matematika"]?.tps ?: emptyList()
    val adaptedForGrade8 = CurriculumBank.adaptTpCodesToRombel(tps, "VIII B")
    assertEquals("8.1", adaptedForGrade8[0].code)

    val adaptedForGrade9 = CurriculumBank.adaptTpCodesToRombel(tps, "IX C")
    assertEquals("9.1", adaptedForGrade9[0].code)
  }

  @Test
  fun testStudentGradeCalculationAndReportCardDescription() {
    val student = StudentGrade(1, "Budi Santoso", "262707001", "H", 90, 85, 80, 85, 85, 90)
    val tps = CurriculumBank.SUBJECTS["Matematika"]?.tps ?: emptyList()
    val summary = student.calculateSummary(tps)
    assertTrue(summary.finalScore > 0)
    assertTrue(summary.reportDescription.contains("Budi Santoso"))
  }
}
