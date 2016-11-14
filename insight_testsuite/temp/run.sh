#!/usr/bin/env bash

# example of the run script for running the fraud detection algorithm with a python file,
# but could be replaced with similar files from any major language

# I'll execute my programs, with the input directory paymo_input and output the files in the directory paymo_output
#python ./src/antifraud.py ./paymo_input/batch_payment.txt ./paymo_input/stream_payment.txt ./paymo_output/output1.txt ./paymo_output/output2.txt ./paymo_output/output3.txt
mkdir -p build/classes
javac -sourcepath src -d ./build/classes src/com/shikha/fraudDetector/*.java 

fileSuffix=${1:-payment}
java -Dfile.encoding=UTF-8 -classpath ./build/classes com.shikha.fraudDetector.PaymentInput ./paymo_input/batch_${fileSuffix}.txt ./paymo_input/stream_${fileSuffix}.txt ./paymo_output/output1.txt ./paymo_output/output2.txt ./paymo_output/output3.txt
 