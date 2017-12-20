package com.amazon.proposalcalculator.enums;

import com.amazon.proposalcalculator.exception.PricingCalculatorException;

public enum InstanceInputColumn {

    DESCRIPTION("Description"),
    REGION("Region"),
    INSTANCES("Instances"),
    ENVIRONMENT("Environment"),
    SAP_INSTANCE_TYPE("SAP Instance Type"),
    CPU("CPU"),
    //ORIGINAL_CPU("Original CPU"),
    CPU_TOLERANCE("CPU Tolerance"),
    SAPS("SAPS"),
    //ORIGINAL_SAPS("Original SAPS"),
    USE_SAP_CERTIFIED_INSTANCES("Use SAP Certified Instances"),
    MONTHLY_UTILIZATION("Monthly Utilization"),
    MEMORY("Memory(GB)"),
    //ORIGINAL_MEMORY("Original Memory"),
    MEMORY_TOLERANCE("Memory Tolerance"),
    STORAGE("Storage(GB)"),
    VOLUME_TYPE("Volume Type"),
    IOPS("IOPS"),
    SNAPSHOT("Snapshot(GB)"),
    ARCHIVE_LOGS_LOCAL_BACKUP("Archive Logs/Local Backup(GB)"),
    S3_BACKUP("S3 Backup(GB)"),
    TERM_TYPE("Term Type"),
    LEASE_CONTRACT_LENGTH("Lease Contract Length"),
    PURCHASE_OPTION("Purchase Option"),
    OFFERING_CLASS("Offering Class"),
    TENANCY("Tenancy"),
    OPERATING_SYSTEM("Operating System"),
    BEGINNING("Beginning"),
    END("End"),
    PRE_INSTALLED_SOFTWARE("Pre Installed S/W"),
    ONLY_CURRENT_GENERATION_INSTANCES("Only Current Generation Instances"),
    USE_BURSTABLE_PERFORMANCE("Use Burstable Performance"),
    BILLING_OPTION("Billing Option");

    private String columnName;

    InstanceInputColumn(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public static InstanceInputColumn getColumnEnum(String columnName) {

        for (InstanceInputColumn column : InstanceInputColumn.values()) {
            if (column.getColumnName().toLowerCase().equals(columnName.toLowerCase())) {
                return column;
            }
        }
        return null;
    }
}
