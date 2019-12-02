package com.csonezp.func_programming;


import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

public class SupplierDemo {

    public static void main(String args[])
    {

        // This function returns a random value.
        Supplier<Double> randomValue = Suppliers.memoize(() -> Math.random());

        // Print the random value using get()
        System.out.println(randomValue.get());
        System.out.println(randomValue.get());
    }
}
