#!/usr/bin/env bash

# Create a Firewall rule to drop all requests from all interfaces.
sudo iptables -A INPUT -j DROP

sleep 30

# Remove all Firewall rules to restore communication.
sudo iptables -F
