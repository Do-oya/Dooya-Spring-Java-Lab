package org.example._ch03_4;

public class Course {
    private final String subject;
    private final int credit;
    private final String grade;

    public Course(String subject, int credit, String grade) {
        this.subject = subject;
        this.credit = credit;
        this.grade = grade;
    }

    public int getCredit() {
        return credit;
    }

    public double multiplyCreditAndCourseGrade() {
        return credit * getGradeToNumber();
    }

    public double getGradeToNumber() {
        return switch (this.grade) {
            case "A+" -> 4.5;
            case "A" -> 4.0;
            case "B+ " -> 3.5;
            case "B" -> 3.0;
            case "C+" -> 2.5;
            case "C" -> 2.0;
            default -> 0;
        };
    }
}
