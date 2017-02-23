package com.example;

import java.util.Arrays;
import java.util.List;

public class EnumProvider {

	public static final int EMPTY_INDEX_SELECTION = -1;
	public static final int EMPTY_FLAG_SELECTION = 0; 
	
	public static List<String> getPersonsOptions() {
		return Arrays.asList("1 adult", "2 adults", "2 adults + children", "2 adults + 2 childrens", "Other");
	}

	public static List<String> getClimateOptionsFlags() {
		return Arrays.asList("Mountains", "Sea", "Forest", "Lake", "Snow", "Tropical", "Other");
	}

	public static List<String> getMealsOptions() {
		return Arrays.asList("Only breakfast", "Breakfast and dinner", "All inclusive", "Other");
	}
	
	public static List<String> getTransportOptions() {
		return Arrays.asList("Airplane", "Bus", "Ship", "Train", "Other");
	}
	
}
