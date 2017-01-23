package com.amazon.proposalcalculator.enums;

import com.amazon.proposalcalculator.exception.PricingCalculatorException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ravanini on 22/01/17.
 */
public class VolumeTypeTest {

    @Test
    public void generalPurposeTest(){
        VolumeType volumeType = VolumeType.getVolumeType("General Purpose");
        assertEquals(VolumeType.General_Purpose, volumeType);
    }

    @Test
    public void magneticTest(){
        VolumeType volumeType = VolumeType.getVolumeType("Magnetic");
        assertEquals(VolumeType.Magnetic, volumeType);
    }

    @Test
    public void piopsTest(){
        VolumeType volumeType = VolumeType.getVolumeType("Provisioned IOPS");
        assertEquals(VolumeType.Provisioned_IOPS, volumeType);
    }

    @Test
    public void coldTest(){
        VolumeType volumeType = VolumeType.getVolumeType("Cold HDD");
        assertEquals(VolumeType.Cold_HDD, volumeType);

    }

    @Test
    public void throughputOptimizedTest(){
        VolumeType volumeType = VolumeType.getVolumeType("Throughput Optimized HDD");
        assertEquals(VolumeType.Throughput_Optimized_HDD, volumeType);
    }

    @Test(expected = PricingCalculatorException.class)
    public void invalidVolumeTypeTest(){
        VolumeType.getVolumeType("banana");
    }
}
