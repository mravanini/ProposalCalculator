package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.DefaultInput;
import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.enums.VolumeType;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;
import com.amazon.proposalcalculator.utils.Constants;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.amazon.proposalcalculator.calculator.CalculatorPredicates.*;

/**
 * Created by ravanini on 22/01/17.
 */
public class StoragePricingCalculator {

    public static final String EBS_IOPS_GROUP = "EBS IOPS";
    public static final String EBS_IO_REQUESTS = "EBS I/O Requests";


    public static float getSnapshotMonthlyPrice(DefaultInput input){
        if (input.getSnapshot() == null || input.getSnapshot().intValue() == 0){
            return 0;
        }

        float price = getPricePerUnit(region(input).and(snapshot()));
        return price * input.getSnapshot();

    }

    public static float getStorageMonthlyPrice(DefaultInput input) {

        if (input.getStorage() == null || input.getStorage().intValue() == 0){

            return 0;
        }

        if (input.getVolumeType() == null){
            input.setVolumeType(VolumeType.General_Purpose.getColumnName());
        }

        return getPricePerUnit(input);
    }

    private static float getPricePerUnit(DefaultInput input){
        switch (VolumeType.getVolumeType(input.getVolumeType())){
            case General_Purpose: {
                float price = getPricePerUnit(region(input).and(volumeType(input)));
                return price * input.getStorage();

            }
            case Throughput_Optimized_HDD:{
                float price = getPricePerUnit(region(input).and(volumeType(input)));
                return price * input.getStorage();

            }
            case Cold_HDD:{
                float price = getPricePerUnit(region(input).and(volumeType(input)));
                return price * input.getStorage();

            }

            case Provisioned_IOPS:{
                float price = getPricePerUnit(region(input).and(volumeType(input)));
                float storagePrice = price * input.getStorage();

                float piopsPerUnit = getPricePerUnit(region(input).and(group(EBS_IOPS_GROUP)));

                return storagePrice + piopsPerUnit * input.getIops();
            }

            case Magnetic:{
                float price = getPricePerUnit(region(input).and(volumeType(input)));
                float storagePrice = price * input.getStorage();

                float piopsPerUnit = getPricePerUnit(region(input).and(group(EBS_IO_REQUESTS)));

                // * 60 seg * 60 min * 24 h * 30 dias = (IOPs que ele usou / 1 milhão de IO ) x price
                float totalPiops = (input.getIops() * 60 * 60 * 24 * 30) /* / 1000000 the ammount in the CSV already divides by million*/;

                return storagePrice + (totalPiops * piopsPerUnit) ;
            }
            default:{
                throw new PricingCalculatorException("The type of EBS " + input.getVolumeType() + " has not yet been calculated");
            }
        }
    }

    private static float getPricePerUnit(Predicate<Price> p){
        List<Price> possibleMatches = Constants.ec2PriceList.stream().filter(
                p
        ).collect(Collectors.toList());

        if (possibleMatches.size() != 1){
            throw new PricingCalculatorException("Invalid amount of EBS price found in price list. Expected = 1; Found = " + possibleMatches.size());
        }

        return possibleMatches.get(0).getPricePerUnit();

    }

}
