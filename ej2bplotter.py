import sys
import matplotlib.pyplot as plt
import numpy as np

if __name__ == '__main__':

    if (len(sys.argv) < 2):
        print("Wrong amount of parameters")
        exit()

    times = []
    positions = []
    velocities = []

    data = np.genfromtxt(".\TP4\datos3.csv", delimiter=",", names=["spaceshipVelocity", "t"])
            
    plt.plot(data['t']-356,data['spaceshipVelocity'],'r.-')
    plt.grid(b=True, which='both', axis='both')
    plt.ylabel('Velocidad de la neve (Km/s)')
    plt.xlabel('Dias desde despegue (Dias)')
    plt.show()
