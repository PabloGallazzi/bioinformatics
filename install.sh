#!/usr/bin/env bash

cd blast

if [ -f swissprot ] && [ -f swissprot.phr ] && [ -f swissprot.pin ] && [ -f swissprot.psq ]; then
    exit 0
fi

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
./ncbi-blast-2.6.0+/bin/makeblastdb -in swissprot -dbtype prot

cd ..