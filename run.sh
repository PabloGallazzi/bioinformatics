#!/usr/bin/env bash

./check.sh

./build.sh

./install.sh

echo "Starting run..."

os=null
./blast/ncbi-blast-2.6.0+/bin/macblastp -h >/dev/null

if [ $? -eq 0 ]; then
    os="mac"
else
    echo "The above error can be SAFELY ignored!"
    os="linux"
fi

java -jar Ex1-jar-with-dependencies.jar
java -jar Ex2-jar-with-dependencies.jar
java -jar Ex2Local-jar-with-dependencies.jar ${os}
java -jar Ex3-jar-with-dependencies.jar
java -jar Ex4-jar-with-dependencies.jar QYHDYSDDGWVNLNRQGFSYQCPQGQVIVAVRSIF
java -jar Ex4-jar-with-dependencies.jar +K+++PR+KL

echo "Finishing run..."

echo "You can verify results at data/output"