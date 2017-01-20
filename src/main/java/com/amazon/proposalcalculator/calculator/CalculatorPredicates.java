package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.DefaultInput;
import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.utils.Constants;

import java.util.function.Predicate;

/**
 * Created by ravanini on 20/01/17.
 */
public class CalculatorPredicates {


    public static Predicate<Price> region(DefaultInput server){
        return p -> p.getLocation() != null && p.getLocation().startsWith(server.getRegion());
    }

    public static Predicate<Price> cpuTolerance(DefaultInput server){
        return p -> p.getvCPU() >= server.getCpu() * ((100 - Constants.config.getCpuTolerance()) / 100);
    }

    public static Predicate<Price> memory(DefaultInput server){
        return p -> p.getMemory() >= server.getMemory() * ((100 - Constants.config.getMemoryTolerance()) / 100);
    }

    public static Predicate<Price> termType(DefaultInput server){
        return p -> p.getTermType() != null && p.getTermType().equals(server.getTermType());
    }

    public static Predicate<Price> offeringClass(DefaultInput server){
        return p -> p.getOfferingClass() != null && p.getOfferingClass().equals(server.getOfferingClass());
    }

    public static Predicate<Price> leaseContractLength(DefaultInput server){
        return p -> p.getLeaseContractLength() != null && p.getLeaseContractLength().equals(server.getLeaseContractLength());
    }

    public static Predicate<Price> purchaseOption(DefaultInput server){
        return p -> p.getPurchaseOption() != null && p.getPurchaseOption().equals(server.getPurchaseOption());
    }

    public static Predicate<Price> operatingSystem(DefaultInput server){
        return p -> p.getOperatingSystem() != null && p.getOperatingSystem().equals(server.getOperatingSystem());
    }

    public static Predicate<Price> tenancy(DefaultInput server){
        return p -> p.getTenancy() != null && p.getTenancy().equals(server.getTenancy());
    }
}
