package com.hostel.student.service;

import com.hostel.student.exception.StudentNotFoundException;
import com.hostel.student.model.Student;

/**
 * Integration Hook for Member 3 (Fee Management Module).
 * 
 * Defines how Fee Management consumes Student details (ID, Name, Course)
 * to associate student ledgers, fee deposits, and pending dues.
 */
public interface FeeModuleHook {

    /**
     * Verifies student existence before recording a fee transaction.
     */
    boolean verifyStudentForFee(String studentId);

    /**
     * Generates a printable fee invoice summary for a student.
     */
    String getStudentFeeRecipientSummary(String studentId) throws StudentNotFoundException;
}
