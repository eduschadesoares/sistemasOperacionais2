#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from os import system
from random import randint

user = []
CPU_time = 100

def insert_user():
    user.append([])


def insert_process():
    if user:
        user_rand = randint(0, len(user)-1)
        user[user_rand].append(0)

def show_info():
    for index, each in enumerate(user):
        print("Usuário {}".format(index + 1))
        for each2 in each:
            print("     - {} %".format(each2))

def scheduler():
    user_num = len(user)
    time_for_user = CPU_time / user_num

    time_control_user = 0
    user_processing = 0

    which_process_in_CPU = 0
    time_control_process = 0

    for i in range (0, CPU_time):

        process_num = len(user[user_processing])
        if process_num > 0:
            time_for_process = int(time_for_user / process_num)

        if time_control_user <= time_for_user:
            if time_control_process < time_for_process:
                try:
                    user[user_processing][which_process_in_CPU] += 1
                except:
                    print("opa")
                time_control_process += 1
            else:
                which_process_in_CPU += 1
                time_control_process = 0

            time_control_user += 1
            
        else:
            user_processing += 1
            time_control_user = 0
            which_process_in_CPU = 0
        
        system("clear")
        show_info()

    
insert_user()
insert_user()
insert_process()
insert_process()
insert_process()
insert_process()
insert_process()
scheduler()