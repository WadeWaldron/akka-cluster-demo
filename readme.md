# Description

This project was inspired in part by the following project:

https://github.com/lightbend/Pi-Akka-Cluster

The original inspiration used a cluster of Raspberry Pi's to
demonstrate an Akka Cluster with LED lights to indicate various
status conditions in the cluster.

The Raspberry Pi example is fun and makes a great demonstration, but
due to the requirement of the Raspberry Pi hardware it isn't something
that can be run by anyone.

This project attempts to take that same basic idea, and create something
that can be run by anyone without requiring additional hardware. It
does this using Vagrant VMs.

# Vagrant

The project includes a vagrant folder. You can navigate to that folder
and run:

```
vagrant up
```

This will build 5 small vms. Each VM is equipped to run the application.

The VMs are setup to run with specific IPs. They will be found at:

- 192.168.33.10
- 192.168.33.20
- 192.168.33.30
- 192.168.33.40
- 192.168.33.50

# Akka Cluster

The source code for this project creates a small Akka cluster. This
cluster has some code that will monitor the state of the cluster and
display it on a webpage which can be accessed at port 8080 for each
VM in the cluster (eg. http://192.168.33.10:8080)

# Deployment

The code must be built and deployed to each node in the cluster. This
can be achieved by running:

```
./buildAndDeploy.sh
```

# Running

You can run the application on each node in the cluster by connecting
to the VM. For example:

```
vagrant ssh vm1
```

Then you simply execute the run script from the vagrant home folder:

```
./run.sh
```

# Simulating Network Partitions

A small script is included in each VM to simulate a network partition.
In the vagrant home folder you will find `simulateNetworkPartition.sh`
If you run this script it will disable the network interface for 30
seconds before restoring it.

# Split Brain Resolver

The project is setup to demonstrate the effect of using Akka
auto-downing by default. It can show how creating a network partition
will result in a split brain scenario.

By changing some of the configuration values (disabling auto-downing
and enabling Split Brain Resolver), you can demonstrate how to properly
deal with unreachable nodes and prevent split brain scenarios.
