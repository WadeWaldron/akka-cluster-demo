#!/usr/bin/env bash

echo "BUILDING UNIVERSAL PACKAGE"
sbt universal:packageBin

echo "COPYING TO vm1"
scp -i vagrant/vagrant_rsa target/universal/akka-cluster-0.1-SNAPSHOT.zip vagrant@192.168.33.10:/home/vagrant/
echo "UNZIPPING ON vm1"
ssh -i vagrant/vagrant_rsa vagrant@192.168.33.10 'unzip -o akka-cluster-0.1-SNAPSHOT.zip'

echo "COPYING TO vm2"
scp -i vagrant/vagrant_rsa target/universal/akka-cluster-0.1-SNAPSHOT.zip vagrant@192.168.33.20:/home/vagrant/
echo "UNZIPPING ON vm2"
ssh -i vagrant/vagrant_rsa vagrant@192.168.33.20 'unzip -o akka-cluster-0.1-SNAPSHOT.zip'

#scp -i vagrant/vagrant_rsa target/universal/akka-cluster-0.1-SNAPSHOT.zip vagrant@192.168.33.20:/home/vagrant/
#scp -i vagrant/vagrant_rsa target/universal/akka-cluster-0.1-SNAPSHOT.zip vagrant@192.168.33.30:/home/vagrant/
#scp -i vagrant/vagrant_rsa target/universal/akka-cluster-0.1-SNAPSHOT.zip vagrant@192.168.33.40:/home/vagrant/
#scp -i vagrant/vagrant_rsa target/universal/akka-cluster-0.1-SNAPSHOT.zip vagrant@192.168.33.50:/home/vagrant/
