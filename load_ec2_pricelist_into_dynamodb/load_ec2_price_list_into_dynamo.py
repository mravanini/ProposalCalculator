# How to:
#- run the script in validation mode:
#python load_ec2_price_list_into_dynamo.py
#- run the script in loading mode:
#python load_ec2_price_list_into_dynamo.py load

#Python 2.7

from __future__ import print_function # Python 2/3 compatibility
from datetime import datetime as dt
import boto3
import json
import logging
import sys
import load_into_dynamodb as load



level_logging = logging.INFO
json_file_name = "index_ec2_prices_short_version.json"
# json_file_name = "index_ec2_prices.json"


class Price_Item():
    def __init__(self):
        self.sku = None
        self.location = None
        self.productFamily = None
        self.vcpu = None
        self.memory = None
        self.operatingSystem = None
        self.instanceType = None
        self.currentGeneration = None
        self.tenancy = None
        self.preInstalledSw = None
        self.fromLocation = None
        self.volumeType = None
        self.servicecode = None
        self.group = None
        self.licenseModel = None
        #stop copying here - all related to price dimensions must not be copied
        self.rateCode = None
        self.termType = None    #   OnDemand | Reserved
        self.purchaseOption = None  #     No Upfront | Partial Upfront | All Upfront
        self.offeringClass = None   #   Standard | Convertible
        self.leaseContractLength = None #   1yr | 3yr
        self.unit = None
        self.price = None
        self.currency = None
        self.description = None

    @staticmethod
    def new_copied_price_item(price_item):
        price_item_aux = Price_Item()
        price_item_aux.sku = price_item.sku
        price_item_aux.location = price_item.location
        price_item_aux.productFamily = price_item.productFamily
        price_item_aux.vcpu = price_item.vcpu
        price_item_aux.memory = price_item.memory
        price_item_aux.operatingSystem = price_item.operatingSystem
        price_item_aux.instanceType = price_item.instanceType
        price_item_aux.currentGeneration = price_item.currentGeneration
        price_item_aux.tenancy = price_item.tenancy
        price_item_aux.preInstalledSw = price_item.preInstalledSw
        price_item_aux.fromLocation = price_item.fromLocation
        price_item_aux.volumeType = price_item.volumeType
        price_item_aux.servicecode = price_item.servicecode
        price_item_aux.group = price_item.group
        price_item_aux.licenseModel = price_item.licenseModel
        #stop copying here - all related to price dimensions must not be copied
        price_item_aux.rateCode = None
        price_item_aux.termType = None
        price_item_aux.purchaseOption = None
        price_item_aux.offeringClass = None
        price_item_aux.leaseContractLength = None
        price_item_aux.unit = None
        price_item_aux.price = None
        price_item_aux.currency = None
        price_item_aux.description = None
        return price_item_aux


def print_records_to_insert(records_to_insert):

    for price_item in records_to_insert:
        print ('terms found:')
        print ( 'sku: '  + price_item.sku)
        print ( 'rateCode: ' + price_item.rateCode)
        print ('unit: ' + price_item.unit)
        print ('currency: ' + price_item.currency)
        print ('price: ' + price_item.price)
        print ('productFamily: ' + price_item.productFamily)
        print ("servicecode: " + price_item.servicecode)

        if (price_item.location is not None):
            print ('location: ' + price_item.location)

        if (price_item.group is not None):
            print ('group: ' + price_item.group)

        if (price_item.vcpu is not None):
            print ('vcpu: ' + price_item.vcpu)
            print ('operatingSystem: ' + price_item.operatingSystem)
            print ('memory: ' + price_item.memory)
            print ('instanceType: ' + price_item.instanceType)
            print ('currentGeneration: ' + price_item.currentGeneration)
            print ('tenancy: ' + price_item.tenancy)
            print ('preInstalledSw: ' + price_item.preInstalledSw)

        if (price_item.licenseModel is not None):
            print ("licenseModel: " + price_item.licenseModel)

        if (price_item.fromLocation is not None):
            print ('fromLocation: ' + price_item.fromLocation)

        print ('termType: ' + price_item.termType)

        if price_item.purchaseOption is not None:
            print ('purchaseOption: ' + price_item.purchaseOption)

        if price_item.leaseContractLength is not None:
            print ('leaseContractLength: ' + price_item.leaseContractLength)

        if price_item.offeringClass is not None:
            print ('offeringClass: ' + price_item.offeringClass)

        if price_item.description is not None:
            print ("description: " + price_item.description)

        if price_item.volumeType is not None:
            print ("volumeType: " + price_item.volumeType)

        print ('-----------------------')


def create_price_items(products):

    records_to_insert = []
    price_item = Price_Item()
    for product, info in products.iteritems():

        if 'sku' in info:

            price_item.sku = info['sku']
            # print ('sku: ' + sku)

        if 'productFamily' in info:

            price_item.productFamily = info['productFamily']
            # print ('productFamily: ' + productFamily)


        if 'attributes' in info:

            for attribute, value in info['attributes'].iteritems():

                if attribute == 'location':
                    price_item.location = value
                if attribute == 'vcpu':
                    price_item.vcpu = value
                if attribute == 'memory':
                    memory_string = value
                    price_item.memory = memory_string.split(" ")[0]
                if attribute == 'operatingSystem':
                    price_item.operatingSystem = value
                if attribute == "instanceType":
                    price_item.instanceType = value
                if attribute == "currentGeneration":
                    price_item.currentGeneration = value
                if attribute == "tenancy":
                    price_item.tenancy = value
                if attribute == "preInstalledSw":
                    price_item.preInstalledSw = value
                if attribute == "fromLocation":
                    price_item.fromLocation = value
                if attribute == "volumeType":
                    price_item.volumeType = value
                if attribute == "servicecode":
                    price_item.servicecode = value
                if attribute == "group":
                    price_item.group = value
                if attribute == "licenseModel":
                    price_item.licenseModel = value


        if price_item.sku is not None and price_item.productFamily is not None and (price_item.location is not None
                or price_item.fromLocation is not None):

            new_price_item = Price_Item.new_copied_price_item(price_item)

            create_on_demand_record(offers, new_price_item, records_to_insert)

            if price_item.productFamily == 'Compute Instance':

                create_reserved_records(offers, price_item, records_to_insert)

            price_item = Price_Item()

    return records_to_insert



def create_on_demand_record(offers, price_item, records_to_insert):

    for jason, values in offers.iteritems():

        if 'terms' in jason:

            if price_item.sku in values['OnDemand']:

                for id, value in values['OnDemand'][price_item.sku].iteritems():

                    if 'priceDimensions' in value:

                        for price_dimensions, details in value['priceDimensions'].values()[0].iteritems():

                            if 'rateCode' in price_dimensions:
                                price_item.rateCode = details

                            if 'unit' in price_dimensions:
                                price_item.unit = details
                                # print ( 'details: ' + details)

                            if 'pricePerUnit' in price_dimensions:
                                for currency, price in details.iteritems():
                                    price_item.currency = currency
                                    # print ( 'currency: ' + currency)
                                    price_item.price = price
                                    # print ( 'price: ' + price)


                            if price_item.rateCode is not None and price_item.unit is not None and price_item.price is \
                                    not None:
                                price_item.termType = 'OnDemand'
                                # print_price_item(price_item)
                                records_to_insert.append(price_item)

            else:
                logging.warning("Service rateCode: " + price_item.sku + " has no on-demand pricing.")



def create_reserved_records(offers, price_item, records_to_insert):

    new_price_item = Price_Item.new_copied_price_item(price_item)

    for jason, values in offers.iteritems():

        if 'terms' in jason:

            if price_item.sku in values['Reserved'] and price_item.productFamily == 'Compute Instance':

                for id, value in values['Reserved'][price_item.sku].iteritems():

                    if 'termAttributes' in value:
                        for term_attribute, term_attribute_value in value['termAttributes'].iteritems():
                            if 'LeaseContractLength' in term_attribute:
                                new_price_item.leaseContractLength = term_attribute_value
                                # print ( 'leaseContractLength: ' + new_price_item.leaseContractLength)

                            if 'OfferingClass' in term_attribute:
                                new_price_item.offeringClass = term_attribute_value
                                # print ( 'leaseContractLength: ' + new_price_item.offeringClass)

                            if 'PurchaseOption' in term_attribute:
                                new_price_item.purchaseOption = term_attribute_value
                                # print ( 'PurchaseOption: ' + new_price_item.purchaseOption)

                    if 'priceDimensions' in value:
                        # for price_dimensions in value['priceDimensions'].values()[0].values():
                        for price_dimensions, details in value['priceDimensions'].values()[0].iteritems():

                            if 'rateCode' in price_dimensions:
                                new_price_item.rateCode = details

                            if 'unit' in price_dimensions:
                                new_price_item.unit = details
                                # print ( 'details: ' + details)

                            if 'description' in price_dimensions:
                                new_price_item.description = details

                            if 'pricePerUnit' in price_dimensions:
                                for currency, price in details.iteritems():
                                    new_price_item.currency = currency
                                    # print ( 'currency: ' + currency)
                                    new_price_item.price = price
                                    # print ( 'price: ' + price)


                            if new_price_item.purchaseOption is not None and new_price_item.rateCode is not None and \
                                            new_price_item.unit is not None and new_price_item.description is not None and new_price_item.currency is not None:
                                new_price_item.termType = 'Reserved'
                                # print_price_item(price_item)
                                records_to_insert.append(new_price_item)

                                new_price_item = Price_Item.new_copied_price_item(price_item)

            else:
                logging.warning("Instance rateCode: " + price_item.sku + " has no reserved pricing.")


def validate_records_to_insert(records_to_insert):

    logging.info('--------------------------------')
    logging.info('Beginning validation:')

    logging.info('List size: ' + str(len(records_to_insert)))

    for price_item in records_to_insert:

        if (price_item.rateCode is None):
            logging.error('RateCode is None for SKU: ' + price_item.sku)

        if (price_item.unit is None):
            logging.error('unit is None for rateCode: ' + price_item.rateCode)

        if (price_item.currency is None):
            logging.error('currency is None for rateCode: ' + price_item.rateCode)

        if (price_item.price is None):
            logging.error('price is None for rateCode: ' + price_item.rateCode)

        if (price_item.productFamily is None):
            logging.error('productFamily is None for rateCode: ' + price_item.rateCode)

        if (price_item.location is None and price_item.fromLocation is None):
            logging.error('location or fromLocation must be filled. RateCode: ' + price_item.rateCode)

        if (price_item.servicecode is None):
            logging.error('servicecode is None for rateCode: ' + price_item.rateCode)

        if (price_item.leaseContractLength is not None and (price_item.leaseContractLength != '1yr' and
                                                                    price_item.leaseContractLength != '3yr')):
            logging.error('Lease contract length not valid. Found: ' + price_item.leaseContractLength + '. RateCode: '
                          + price_item.rateCode)

        if (price_item.offeringClass is not None and (price_item.offeringClass.lower() != 'standard' and
                                                                    price_item.offeringClass.lower() != 'convertible')):
            logging.error('OfferingClass not valid. Found: ' + price_item.offeringClass + '. RateCode: '
                          + price_item.rateCode)

        if (price_item.operatingSystem is not None and (price_item.operatingSystem.lower() != 'suse' and
                                                              price_item.operatingSystem.lower() != 'windows' and
                                                                price_item.operatingSystem.lower() != 'rhel' and
                                                                price_item.operatingSystem.lower() != 'linux')):

            # if (price_item.operatingSystem.lower() == 'na' and price_item.tenancy.lower() != 'na'):

            logging.error('operatingSystem not valid. Found: ' + price_item.operatingSystem + '. RateCode: '
                          + price_item.rateCode)

        if (price_item.preInstalledSw is not None and (price_item.preInstalledSw.lower() != 'na' and
                                                                price_item.preInstalledSw.lower() != 'sql std' and
                                                                price_item.preInstalledSw.lower() != 'sql web' and
                                                                price_item.preInstalledSw.lower() != 'sql ent')):
            logging.error('preInstalledSw not valid. Found: ' + price_item.preInstalledSw + '. RateCode: '
                          + price_item.rateCode)

        if (price_item.purchaseOption is not None and (price_item.purchaseOption.lower() != 'no upfront' and
                                                               price_item.purchaseOption.lower() != 'all upfront' and
                                                               price_item.purchaseOption.lower() != 'partial upfront')):
            logging.error('purchaseOption not valid. Found: ' + price_item.purchaseOption + '. RateCode: '
                          + price_item.rateCode)


        if (price_item.location is not None and (price_item.location.lower() != 'us east (ohio)' and
                                                         price_item.location.lower() != 'us west (oregon)' and
                                                         price_item.location.lower() != 'us west (n. california)' and
                                                         price_item.location.lower() != 'us east (n. virginia)' and
                                                         price_item.location.lower() != 'asia pacific (mumbai)' and
                                                         price_item.location.lower() != 'asia pacific (seoul)' and
                                                         price_item.location.lower() != 'asia pacific (singapore)' and
                                                         price_item.location.lower() != 'asia pacific (sydney)' and
                                                         price_item.location.lower() != 'asia pacific (tokyo)' and
                                                         price_item.location.lower() != 'aws govcloud (us)' and
                                                         price_item.location.lower() != 'canada (central)' and
                                                         price_item.location.lower() != 'china (beijing)' and
                                                         price_item.location.lower() != 'eu (frankfurt)' and
                                                         price_item.location.lower() != 'eu (ireland)' and
                                                         price_item.location.lower() != 'eu (london)' and
                                                         price_item.location.lower() != 'south america (sao paulo)')):
            logging.error('location not valid. Found: ' + price_item.location + '. RateCode: '
                          + price_item.rateCode)

        if (price_item.volumeType is not None and (price_item.volumeType.lower() != 'magnetic' and
                                                               price_item.volumeType.lower() != 'general purpose' and
                                                               price_item.volumeType.lower() != 'provisioned iops' and
                                                               price_item.volumeType.lower() != 'throughput optimized '
                                                                                              'hdd' and
                                                           price_item.volumeType.lower() != 'cold hdd')):
            logging.error('volumeType not valid. Found: ' + price_item.volumeType + '. RateCode: '
                          + price_item.rateCode)


    logging.info('validation finished')


def enable_logging():

    logging_file_name = dt.now().strftime('logs/dynamodb-load-%m%d%Y.log')

    logging.basicConfig(filename=logging_file_name, level=level_logging, format= '%(asctime)s %(levelname)s %('
                                                                                 'message)s')
    log_msg = 'Logging enabled in file: %s' % (logging_file_name)
    logging.info('==============================================')
    logging.info(log_msg)
    print (log_msg)


def is_load_into_dynamodb(argv):
    if (len(argv) > 1):
        if 'load' in argv[1]:
            return True

    return False



def load_dynamodb_table(dynamodb, ec2_price_list_table_name, records_to_insert):

    logging.info('Inserting json into dynamoDB table: ' + ec2_price_list_table_name)
    logging.info('Expected amount of records to be inserted: ' + str(len(records_to_insert)))

    ec2_price_list_table = dynamodb.Table(ec2_price_list_table_name)

    with ec2_price_list_table.batch_writer() as batch:
        for price_item in records_to_insert:

            price_item_string = load.build_string_of_price_item(price_item)

            batch.put_item(
                price_item_string
            )


with open(json_file_name) as json_file:

    enable_logging()

    dynamodb = boto3.resource('dynamodb', region_name='us-west-2')

    offers = json.load(json_file)

    ec2_price_list_table_name = load.create_dynamodb_table(dynamodb, offers)

    is_load_into_dynamodb_table = is_load_into_dynamodb(sys.argv)


    for jason, products in offers.iteritems():

        if 'products' in jason:

            records_to_insert = create_price_items(products)

            validate_records_to_insert(records_to_insert)

            # print_records_to_insert(records_to_insert)

            if (is_load_into_dynamodb_table):

                load_dynamodb_table(dynamodb, ec2_price_list_table_name, records_to_insert)

                print ("Process finished.")
                logging.info("Process finished")


