package com.hostel.student.service;

import com.hostel.student.exception.StudentNotFoundException;

/**
 * Integration Hook for Member 4 (Complaint Management Module).
 * 
 * Defines how Complaint Management references student identifiers and
 * contact phone numbers when a resident registers a maintenance ticket or grievance.
 */
public interface ComplaintModuleHook {

    /**
     * Validates that the complainant is a registered hostel student.
     */
    boolean validateComplainant(String studentId);

    /**
     * Retrieves emergency phone number of the complainant for urgent follow-up.
     */
    String getComplainantContact(String studentId) throws StudentNotFoundException;
}
