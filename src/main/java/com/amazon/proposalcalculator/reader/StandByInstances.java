package com.amazon.proposalcalculator.reader;

import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.beanutils.BeanUtils;

import com.amazon.proposalcalculator.bean.Price;
import com.amazon.proposalcalculator.utils.Constants;

public class StandByInstances {

	public static List<Price> generate(List<Price> beanList)
			throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

		Predicate<Price> predicate = c4LargeToBeCloned();

		List<Price> t2NanoToBeClonedList = beanList.stream().filter(predicate).collect(Collectors.toList());

		beanList = cloneT2Nano(beanList, t2NanoToBeClonedList);

		return beanList;
	}

	private static List<Price> cloneT2Nano(List<Price> beanList, List<Price> t2NanoToBeClonedList)
			throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

		for (Price t2Nano : t2NanoToBeClonedList) {

			Price newPrice = (Price) BeanUtils.cloneBean(t2Nano);

			Price standbyInstance = convertToDR(newPrice);

			beanList.add(standbyInstance);

		}

		return beanList;

	}

	private static Price convertToDR(Price newPrice) {
		newPrice.setPricePerUnit(0);
		newPrice.setInstanceType(Constants.STAND_BY_INSTANCE_TYPE);
		newPrice.setvCPU(0);
		newPrice.setMemory(0);

		String sku = newPrice.getInstanceType() + newPrice.getLocation() + newPrice.getOperatingSystem()
				+ newPrice.getLicenseModel();

		String skuHash = getMD5Hash(sku, 16);

		newPrice.setSku(skuHash);

		return newPrice;

	}

	private static Predicate<Price> c4LargeToBeCloned() {
		return p -> (p.getInstanceType() != null
				&& p.getInstanceType().equalsIgnoreCase(Constants.STAND_BY_INSTANCE_TYPE_TEMPLATE)
				&& p.getTenancy() != null
				&& p.getTenancy().equalsIgnoreCase(Constants.STAND_BY_INSTANCE_TENANCY_TEMPLATE));
	}

	/**
	 * Returns a hexadecimal encoded MD5 hash for the input String.
	 */
	private static String getMD5Hash(String data, int size) {
		String result = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");

			byte[] hash = digest.digest(data.getBytes("UTF-8"));

			return bytesToHex(hash, size); // make it printable

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	private static String bytesToHex(byte[] hash, int size) {
		return DatatypeConverter.printHexBinary(hash).substring(0, size);
	}

}
