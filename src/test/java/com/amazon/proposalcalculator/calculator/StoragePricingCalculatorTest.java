package com.amazon.proposalcalculator.calculator;

import com.amazon.proposalcalculator.bean.DefaultInput;
import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.enums.VolumeType;
import com.amazon.proposalcalculator.exception.PricingCalculatorException;
import com.amazon.proposalcalculator.utils.Constants;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by ravanini on 22/01/17.
 */
public class StoragePricingCalculatorTest {

    @Test(expected = PricingCalculatorException.class)
    public void notValidEBSStorageTypeTest(){

        DefaultInput input = new DefaultInput();
        input.setStorage(100);
        input.setVolumeType("banana");

        StoragePricingCalculator.getStorageMonthlyPrice(input);
    }

    @Test
    public void emptyStorageAmountTest(){

        DefaultInput input = new DefaultInput();
        input.setStorage(null);

        double price = StoragePricingCalculator.getStorageMonthlyPrice(input);

        assertEquals(0, price, 0);

        //--

        input.setStorage(0);

        price = StoragePricingCalculator.getStorageMonthlyPrice(input);

        assertEquals(0, price, 0);

    }

    @Test
    public void emptyVolumeTypeTest() {

        DefaultInput input = new DefaultInput();
        input.setStorage(100);
        input.setRegion("South America (Sao Paulo)");
        //input.setVolumeType("General Purpose");

        StoragePricingCalculator.getStorageMonthlyPrice(input);

        assertEquals(VolumeType.General_Purpose.getColumnName(), input.getVolumeType());
    }

    @Test
    public void generalPurposeInNVirginiaTest() {

        DefaultInput input = new DefaultInput();
        input.setStorage(100);
        input.setRegion("US East (N. Virginia)");
        input.setVolumeType("General Purpose");

        double price = StoragePricingCalculator.getStorageMonthlyPrice(input);

        assertEquals(10, price, 0);
    }

    @Test
    public void throughputOptTokioTest() {

        DefaultInput input = new DefaultInput();
        input.setStorage(500);
        input.setRegion("Asia Pacific (Tokyo)");
        input.setVolumeType("Throughput Optimized HDD");

        double price = StoragePricingCalculator.getStorageMonthlyPrice(input);

        assertEquals(27, price, 0);
    }

    @Test
    public void throughputOptNVirginiaTest() {

        DefaultInput input = new DefaultInput();
        input.setStorage(1000);
        input.setRegion("US East (N. Virginia)");
        input.setVolumeType("Throughput Optimized HDD");

        double price = StoragePricingCalculator.getStorageMonthlyPrice(input);

        assertEquals(45, price, 0);
    }

    @Test
    public void coldGovCloudTest() {

        DefaultInput input = new DefaultInput();
        input.setStorage(700);
        input.setRegion("AWS GovCloud (US)");
        input.setVolumeType("Cold HDD");

        double price = StoragePricingCalculator.getStorageMonthlyPrice(input);

        assertEquals(21, price, 0);
    }

    @Test
    public void piopsLondonTest() {

        DefaultInput input = new DefaultInput();
        input.setStorage(50);
        input.setIops(200);
        input.setRegion("EU (London)");
        input.setVolumeType("Provisioned IOPS");

        double price = StoragePricingCalculator.getStorageMonthlyPrice(input);

        assertEquals(22.45, price, 1);
    }

    @Test
    public void magneticFrankfurtTest() {

        DefaultInput input = new DefaultInput();
        input.setStorage(80);
        input.setIops(120);
        input.setRegion("EU (Frankfurt)");
        input.setVolumeType("Magnetic");

        double price = StoragePricingCalculator.getStorageMonthlyPrice(input);

        assertEquals(23.38, price, 1);
    }

    @Test
    public void nullOrZeroSnapshotTest() {
        DefaultInput input = new DefaultInput();
        input.setSnapshot(null);

        double price = StoragePricingCalculator.getSnapshotMonthlyPrice(input);

        Assert.assertEquals(0, price, 0);

        //--
        input.setSnapshot(0);

        price = StoragePricingCalculator.getSnapshotMonthlyPrice(input);

        Assert.assertEquals(0, price, 0);
    }

    @Test
    public void saoPauloSnapshotTest() {
        DefaultInput input = new DefaultInput();
        input.setSnapshot(100);
        input.setRegion("South America (Sao Paulo)");

        double price = StoragePricingCalculator.getSnapshotMonthlyPrice(input);

        Assert.assertEquals(6.8, price, 1);

    }

    @Test
    public void virginiaSnapshotTest() {
        DefaultInput input = new DefaultInput();
        input.setSnapshot(500);
        input.setRegion("US East (N. Virginia)");

        double price = StoragePricingCalculator.getSnapshotMonthlyPrice(input);

        Assert.assertEquals(25, price, 1);

    }

    @BeforeClass
    public static void beforeClass(){
        List<Price> priceList = new ArrayList<>();

        priceList.add(getPrice("US East (N. Virginia)", "General Purpose", 0.1000000000f));

        priceList.add(getPrice("South America (Sao Paulo)", "General Purpose", 0.1900000000f));

        priceList.add(getPrice("US West (N. California)", "General Purpose", 0.1200000000f));

        //--
        priceList.add(getPrice("Asia Pacific (Tokyo)", "Throughput Optimized HDD", 0.0540000000f));

        priceList.add(getPrice("US East (N. Virginia)", "Throughput Optimized HDD", 0.0450000000f));

        //--
        priceList.add(getPrice("AWS GovCloud (US)", "Cold HDD", 0.0300000000f));

        priceList.add(getPrice("Asia Pacific (Mumbai)", "Cold HDD", 0.0290000000f));

        //--

        priceList.add(getPrice("EU (London)", "Provisioned IOPS", 0.1450000000f));
        priceList.add(getPrice("EU (London)", 0.0760000000f, StoragePricingCalculator.EBS_IOPS_GROUP));

        priceList.add(getPrice("Asia Pacific (Seoul)", "Provisioned IOPS", 0.1278000000f));
        priceList.add(getPrice("Asia Pacific (Seoul)", 0.0666000000f, StoragePricingCalculator.EBS_IOPS_GROUP));

        //--

        priceList.add(getPrice("EU (Frankfurt)", "Magnetic", 0.0590000000f));
        priceList.add(getPrice("EU (Frankfurt)", 0.0000000590f, StoragePricingCalculator.EBS_IO_REQUESTS));

        priceList.add(getPrice("US East (Ohio)", "Magnetic", 0.0500000000f));
        priceList.add(getPrice("US East (Ohio)", 0.0000000500f, StoragePricingCalculator.EBS_IO_REQUESTS));

        //--
        //Snapshots
        priceList.add(getPrice("US East (N. Virginia)", 0.0500000000f));

        priceList.add(getPrice("South America (Sao Paulo)", 0.0680000000f));

        priceList.add(getPrice("US West (N. California)", 0.0500000000f));

        priceList.add(getPrice("Asia Pacific (Tokyo)", 0.0540000000f));

        //--

        Constants.ec2PriceList = priceList;
    }

    private static Price getPrice(String region, double pricePerUnit, String group) {
        Price p1 = new Price();
        p1.setLocation(region);
        p1.setPricePerUnit(pricePerUnit);
        p1.setGroup(group);
        return p1;

    }

    private static Price getPrice(String region, String volumeType, double pricePerUnit) {

        Price p1 = new Price();
        p1.setLocation(region);
        p1.setVolumeType(volumeType);
        p1.setPricePerUnit(pricePerUnit);
        return p1;
    }

    private static Price getPrice(String region, double pricePerUnit) {
        Price p1 = new Price();
        p1.setLocation(region);
        p1.setPricePerUnit(pricePerUnit);
        p1.setProductFamily("Storage Snapshot");
        return p1;

    }

}
