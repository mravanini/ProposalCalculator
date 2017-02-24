package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.InstanceInput;
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
    
    public static double getArchiveLogsLocalBackupMonthlyPrice(InstanceInput input){
        if (input.getArchiveLogsLocalBackup() == null || input.getArchiveLogsLocalBackup().intValue() == 0) {
            return 0;
        }

        return getST1PricePerUnit(input);

    }
    
    //TODO load s3 price from csv
    public static double getS3BackupMonthlyPrice(InstanceInput input){
        if (input.getS3Backup() == null || input.getS3Backup().intValue() == 0){
            return 0;
        }

        //double price = getPricePerUnit(region(input).and(snapshot()));
        double price = 0.03;
        return price * input.getS3Backup();
    }

    public static double getSnapshotMonthlyPrice(InstanceInput input){
        if (input.getSnapshot() == null || input.getSnapshot().intValue() == 0){
            return 0;
        }

        double price = getPricePerUnit(region(input).and(snapshot()));
        return price * input.getSnapshot();

    }

    public static double getStorageMonthlyPrice(InstanceInput input) {

        if (input.getStorage() == null || input.getStorage().intValue() == 0){

            return 0;
        }

        if (input.getVolumeType() == null){
            input.setVolumeType(VolumeType.General_Purpose.getColumnName());
        }

        return getPricePerUnit(input);
    }

    private static double getPricePerUnit(InstanceInput input){
        switch (VolumeType.getVolumeType(input.getVolumeType())){
            case General_Purpose: {
                double price = getPricePerUnit(region(input).and(volumeType(input)));
                return price * input.getStorage();

            }
            case Throughput_Optimized_HDD:{
                double price = getPricePerUnit(region(input).and(volumeType(input)));
                return price * input.getStorage();

            }
            case Cold_HDD:{
                double price = getPricePerUnit(region(input).and(volumeType(input)));
                return price * input.getStorage();

            }

            case Provisioned_IOPS:{
                double price = getPricePerUnit(region(input).and(volumeType(input)));
                double storagePrice = price * input.getStorage();

                double piopsPerUnit = getPricePerUnit(region(input).and(group(EBS_IOPS_GROUP)));

                return storagePrice + piopsPerUnit * input.getIops();
            }

            case Magnetic:{
                double price = getPricePerUnit(region(input).and(volumeType(input)));
                double storagePrice = price * input.getStorage();

                double piopsPerUnit = getPricePerUnit(region(input).and(group(EBS_IO_REQUESTS)));

                // * 60 seg * 60 min * 24 h * 30 dias = (IOPs que ele usou / 1 milhão de IO ) x price
                double totalPiops = (input.getIops() * 60 * 60 * 24 * 30) /* / 1000000 the ammount in the CSV already divides by million*/;

                return storagePrice + (totalPiops * piopsPerUnit) ;
            }
            default:{
                throw new PricingCalculatorException("The type of EBS " + input.getVolumeType() + " has not yet been calculated");
            }
        }
    }
    
	private static double getST1PricePerUnit(InstanceInput input) {
		double price = getPricePerUnit(region(input).and(st1()));
		return price * input.getArchiveLogsLocalBackup();
	}

    private static double getPricePerUnit(Predicate<Price> p){
        List<Price> possibleMatches = Constants.ec2PriceList.stream().filter(
                p
        ).collect(Collectors.toList());

        if (possibleMatches.size() != 1){
            throw new PricingCalculatorException("Invalid amount of EBS price found in price list. Expected = 1; Found = " + possibleMatches.size());
        }

        return possibleMatches.get(0).getPricePerUnit();

    }

}
