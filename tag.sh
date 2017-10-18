#!/usr/bin/env bash

./check.sh

./build.sh

cd blast

if [ -f swissprot.gz ]; then
    rm swissprot.gz
fi

if [ -f swissprot ]; then
    rm swissprot
fi

if [ -f swissprot.phr ]; then
    rm swissprot.phr
fi

if [ -f swissprot.pin ]; then
    rm swissprot.pin
fi

if [ -f swissprot.psq ]; then
    rm swissprot.psq
fi

wget ftp://ftp.ncbi.nlm.nih.gov/blast/db/FASTA/swissprot.gz
gunzip swissprot.gz

cd ..

rm -rf bioinformatics

mkdir bioinformatics

sed '/^.\/check.sh/ d' run.sh  |  sed '/^.\/build.sh/ d' |  sed '/^.\/install.sh/ d' > bioinformatics/run.sh

sed -i '' '11i\
./ncbi-blast-2.6.0+/bin/${os}makeblastdb -in swissprot -dbtype prot' bioinformatics/run.sh

chmod +x bioinformatics/run.sh

cp -R blast bioinformatics

if [ -f blast/swissprot.gz ]; then
    rm blast/swissprot.gz
fi

cp -R data bioinformatics

cp Ex1-jar-with-dependencies.jar bioinformatics
cp Ex2-jar-with-dependencies.jar bioinformatics
cp Ex2Local-jar-with-dependencies.jar bioinformatics
cp Ex3-jar-with-dependencies.jar bioinformatics

env GZIP=-9 tar cvzf bioinformatics.tar.gz bioinformatics