#!/usr/bin/env bash

echo "BUILDING UNIVERSAL PACKAGE"
sbt universal:packageBin

chmod 600 vagrant/vagrant_rsa

echo "COPYING TO vm1"
scp -i vagrant/vagrant_rsa target/universal/akka-cluster-0.1-SNAPSHOT.zip vagrant@192.168.33.10:/home/vagrant/
echo "UNZIPPING ON vm1"
ssh -i vagrant/vagrant_rsa vagrant@192.168.33.10 'unzip -o akka-cluster-0.1-SNAPSHOT.zip'

echo "COPYING TO vm2"
scp -i vagrant/vagrant_rsa target/universal/akka-cluster-0.1-SNAPSHOT.zip vagrant@192.168.33.20:/home/vagrant/
echo "UNZIPPING ON vm2"
ssh -i vagrant/vagrant_rsa vagrant@192.168.33.20 'unzip -o akka-cluster-0.1-SNAPSHOT.zip'

echo "COPYING TO vm3"
scp -i vagrant/vagrant_rsa target/universal/akka-cluster-0.1-SNAPSHOT.zip vagrant@192.168.33.30:/home/vagrant/
echo "UNZIPPING ON vm3"
ssh -i vagrant/vagrant_rsa vagrant@192.168.33.30 'unzip -o akka-cluster-0.1-SNAPSHOT.zip'

echo "COPYING TO vm4"
scp -i vagrant/vagrant_rsa target/universal/akka-cluster-0.1-SNAPSHOT.zip vagrant@192.168.33.40:/home/vagrant/
echo "UNZIPPING ON vm4"
ssh -i vagrant/vagrant_rsa vagrant@192.168.33.40 'unzip -o akka-cluster-0.1-SNAPSHOT.zip'

echo "COPYING TO vm5"
scp -i vagrant/vagrant_rsa target/universal/akka-cluster-0.1-SNAPSHOT.zip vagrant@192.168.33.50:/home/vagrant/
echo "UNZIPPING ON vm5"
ssh -i vagrant/vagrant_rsa vagrant@192.168.33.50 'unzip -o akka-cluster-0.1-SNAPSHOT.zip'
