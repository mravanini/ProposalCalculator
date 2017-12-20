from __future__ import print_function # Python 2/3 compatibility
from botocore.exceptions import ClientError
import json
import decimal
import logging


#Python 2.7

def create_dynamodb_table_name(offers):

    ec2_price_list_table_name = None
    for jason, version in offers.iteritems():
        if 'version' in jason:
            ec2_price_list_table_name = 'ec2-price-list-' + version
            # print ('ec2_price_list_table_name: ' + ec2_price_list_table_name)
            break

    return ec2_price_list_table_name

def create_dynamodb_table(dynamodb, offers):

    # ec2_price_list_table_name = dt.now().strftime('ec2-price-list-%m%d%Y')
    ec2_price_list_table_name = create_dynamodb_table_name(offers)


    try:
        table = dynamodb.create_table(
            TableName=ec2_price_list_table_name,
            KeySchema=[
                {
                    'AttributeName': 'rateCode',
                    'KeyType': 'HASH'
                }
            ],
            AttributeDefinitions=[
                {
                    'AttributeName': 'rateCode',
                    'AttributeType': 'S'
                },
                {
                    'AttributeName': 'location',
                    'AttributeType': 'S'
                },
                {
                    'AttributeName': 'vcpu',
                    'AttributeType': 'N'
                },
                {
                    'AttributeName': 'memory',
                    'AttributeType': 'N'
                },
                {
                    'AttributeName': 'operatingSystem',
                    'AttributeType': 'S'
                },
                {
                    'AttributeName': 'termType',
                    'AttributeType': 'S'
                },

            ],
            GlobalSecondaryIndexes=[
                {
                    'IndexName': 'location-index',
                    'KeySchema': [
                        {
                            'AttributeName': 'location',
                            'KeyType': 'HASH'
                        },
                    ],
                    'Projection': {
                        'ProjectionType': 'ALL'
                    },
                    'ProvisionedThroughput': {
                        'ReadCapacityUnits': 30,
                        'WriteCapacityUnits': 80
                    }
                },
                {
                    'IndexName': 'vcpu-index',
                    'KeySchema': [
                        {
                            'AttributeName': 'vcpu',
                            'KeyType': 'HASH'
                        },
                    ],
                    'Projection': {
                        'ProjectionType': 'ALL'
                    },
                    'ProvisionedThroughput': {
                        'ReadCapacityUnits': 30,
                        'WriteCapacityUnits': 80
                    }
                },
                {
                    'IndexName': 'memory-index',
                    'KeySchema': [
                        {
                            'AttributeName': 'memory',
                            'KeyType': 'HASH'
                        },
                    ],
                    'Projection': {
                        'ProjectionType': 'ALL'
                    },
                    'ProvisionedThroughput': {
                        'ReadCapacityUnits': 30,
                        'WriteCapacityUnits': 80
                    }
                },
                {
                    'IndexName': 'operatingSystem-index',
                    'KeySchema': [
                        {
                            'AttributeName': 'operatingSystem',
                            'KeyType': 'HASH'
                        },
                    ],
                    'Projection': {
                        'ProjectionType': 'ALL'
                    },
                    'ProvisionedThroughput': {
                        'ReadCapacityUnits': 30,
                        'WriteCapacityUnits': 80
                    }
                },
                {
                    'IndexName': 'termType-index',
                    'KeySchema': [
                        {
                            'AttributeName': 'termType',
                            'KeyType': 'HASH'
                        },
                    ],
                    'Projection': {
                        'ProjectionType': 'ALL'
                    },
                    'ProvisionedThroughput': {
                        'ReadCapacityUnits': 30,
                        'WriteCapacityUnits': 80
                    }
                },
            ],
            ProvisionedThroughput={
                'ReadCapacityUnits': 30,
                'WriteCapacityUnits': 80
            }
        )

        msg_waiting = 'Waiting for the table %s to be created. This might take a ' \
                      'while...' %(ec2_price_list_table_name)
        print (msg_waiting)
        logging.info(msg_waiting)
        # Wait until the table exists.
        table.meta.client.get_waiter('table_exists').wait(TableName=ec2_price_list_table_name)

        msg_created = 'Table %s created.' % (ec2_price_list_table_name)
        print (msg_created)
        logging.info(msg_created)

    except ClientError as ex:
        if ex.response['Error']['Code'] == 'ResourceInUseException':
            msg_error = 'Table %s already exists.' % (ec2_price_list_table_name)
            print (msg_error)
            logging.warning(msg_error)
        else:
            raise ex

    return ec2_price_list_table_name


def build_string_of_price_item(price_item):

    was_memory_before = "\""
    was_vcpu_before = "\""


    # Novo ID:
    # location
    # termType
    # servicecode
    # tenancy
    # operatingSystem
    # leaseContractLength
    # purchaseOption
    # offeringClass
    # preInstalledSw?? O que ele coloca quando nao especificamos nada? NA? Sim.
    
    new_id = "%s%s%s%s%s%s%s%s%s" %(price_item.location or 'NA',
                                    price_item.termType,
                                    price_item.servicecode,
                                    price_item.tenancy or 'NA',
                                    price_item.operatingSystem or 'NA',
                                    price_item.leaseContractLength or 'NA',
                                    price_item.purchaseOption or 'NA',
                                    price_item.offeringClass or 'NA',
                                    price_item.preInstalledSw or 'NA')
    
    print (new_id)

    price_item_string = "{"
    price_item_string += ('\"rateCode\": \"'+ price_item.rateCode)

    if (price_item.location is not None):
        price_item_string += ('\", \"location\": \"'+ price_item.location)

    if (price_item.instanceType is not None):
        price_item_string += ('\", \"instanceType\": \"'+ price_item.instanceType)

    if (price_item.sku is not None):
        price_item_string += ('\", \"sku\": \"'+ price_item.sku)
    if (price_item.operatingSystem is not None):
        price_item_string += ('\", \"operatingSystem\": \"'+ price_item.operatingSystem)
    if (price_item.termType is not None):
        price_item_string += ('\", \"termType\": \"'+ price_item.termType)
    if (price_item.productFamily is not None):
        price_item_string += ('\", \"productFamily\": \"'+ price_item.productFamily)
    if (price_item.currentGeneration is not None):
        price_item_string += ('\", \"currentGeneration\": \"'+ price_item.currentGeneration)
    if (price_item.servicecode is not None):
        price_item_string += ('\", \"servicecode\": \"'+ price_item.servicecode)
    if (price_item.group is not None):
        price_item_string += ('\", \"group\": \"'+ price_item.group)
    if (price_item.tenancy is not None):
        price_item_string += ('\", \"tenancy\": \"'+ price_item.tenancy)
    if (price_item.preInstalledSw is not None):
        price_item_string += ('\", \"preInstalledSw\": \"'+ price_item.preInstalledSw)
    if (price_item.fromLocation is not None):
        price_item_string += ('\", \"fromLocation\": \"'+ price_item.fromLocation)
    if (price_item.volumeType is not None):
        price_item_string += ('\", \"volumeType\": \"'+ price_item.volumeType)
    if (price_item.licenseModel is not None):
        price_item_string += ('\", \"licenseModel\": \"'+ price_item.licenseModel)
    if (price_item.leaseContractLength is not None):
        price_item_string += ('\", \"leaseContractLength\": \"'+ price_item.leaseContractLength)
    if (price_item.offeringClass is not None):
        price_item_string += ('\", \"offeringClass\": \"'+ price_item.offeringClass)
    if (price_item.purchaseOption is not None):
        price_item_string += ('\", \"purchaseOption\": \"'+ price_item.purchaseOption)
    if (price_item.unit is not None):
        price_item_string += ('\", \"unit\": \"'+ price_item.unit)
    if (price_item.currency is not None):
        price_item_string += ('\", \"currency\": \"'+ price_item.currency)
    if (price_item.price is not None):
        price_item_string += ('\", \"price\": \"'+ price_item.price)
    if (price_item.description is not None):
        price_item_string += ('\", \"description\": \"'+ price_item.description)

    if (price_item.vcpu is not None):
        price_item_string += '\", \"vcpu\": %d ' % (decimal.Decimal(price_item.vcpu.replace(",", "")))
        was_vcpu_before = ""

    if (price_item.memory is not None and price_item.memory != "NA"): # dedicated hosts have memory: NA
        price_item_string += ' %s, \"memory\": %d' %(was_vcpu_before, decimal.Decimal(
            price_item.memory.replace(",", "")) )
        was_memory_before = ""
        was_vcpu_before = "\""


    price_item_string += "%s}" % (was_memory_before and was_vcpu_before )

    return json.loads(price_item_string)
