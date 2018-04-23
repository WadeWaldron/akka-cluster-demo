#!/usr/bin/env bash

sudo ip link set enp0s8 down

sleep 30

sudo ip link set enp0s8 up
