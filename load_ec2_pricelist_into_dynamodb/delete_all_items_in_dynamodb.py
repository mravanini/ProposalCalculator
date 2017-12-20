from __future__ import print_function # Python 2/3 compatibility
from datetime import datetime as dt
from botocore.exceptions import ClientError
import boto3
import json
import decimal
import logging
import sys

dynamodb = boto3.resource('dynamodb', region_name='us-west-2')

ec2_price_list_table_name = 'ec2-price-list-20171007002655'


for row in temperatures.scan(scan_filter={'Id': condition.EQ(ID_TO_DELETE)}):
	count += 1
	row.delete()

print 'Deleted {0} rows.'.format(count)