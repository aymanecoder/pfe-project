package com.group8.projectpfe.mappers;

public interface Mapper<A,B>{
    B mapTo(A a);
    A mapFrom(B b);
}
