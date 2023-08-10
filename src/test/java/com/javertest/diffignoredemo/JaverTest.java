package com.javertest.diffignoredemo;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.metamodel.annotation.DiffIgnore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class JaverTest {

    public static Javers JAVERS = JaversBuilder.javers().build();
    private static Logger log = LoggerFactory.getLogger(JaverTest.class);

    @Test
    void testDiffIgnoreAtNestedFieldWithObjectClass() {
        Student student = new Student();
        Address address = new Address();
        address.addressLine = "Somewhere";
        address.country = "US";
        student.address = address;

        Student student2 = new Student();
        Address address2 = new Address();
        address2.addressLine = "Somewhere Diff";
        address2.country = "US";
        student2.address = address2;

        Diff diff = JAVERS.compare(student, student2);

        log.info(diff.prettyPrint());

        Assertions.assertFalse(diff.hasChanges());
    }


    static class Student {
        // if I change this to Address or SuperAddress then it will work
        private Object address;

        @Override
        public String toString() {
            return "Student{" +
                "address=" + address +
                '}';
        }
    }

    static class Address extends SuperAddress{
        @DiffIgnore
        private String addressLine;
        private String country;

        @Override
        public String toString() {
            return "Address{" +
                "addressLine='" + addressLine + '\'' +
                ", country='" + country + '\'' +
                '}';
        }
    }

    public static class SuperAddress {

    }
}
