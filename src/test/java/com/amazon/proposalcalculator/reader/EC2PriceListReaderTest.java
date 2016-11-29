package com.amazon.proposalcalculator.reader;

import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.reader.EC2PriceListReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by ravanini on 29/11/16.
 */
public class EC2PriceListReaderTest {


    @Test
    public void test() throws IOException {

        EC2PriceListReader ec2PriceListReader = new EC2PriceListReader();
        List<Price> prices = ec2PriceListReader.read(false);
        Assert.assertNotNull(prices);
        //Assert.assertEquals(91799, prices.size());
    }
}
