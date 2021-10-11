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

    data = np.genfromtxt(".\TP4\datos6p4.csv", delimiter=",", names=["spaceshipVel", "marsMinDist"])
            
    plt.plot(data['spaceshipVel'],data['marsMinDist'],'r.-')
    plt.grid(b=True, which='both', axis='both')
    plt.xlabel('Velocidad de salida de la neve (Km/s)')
    plt.ylabel('Distancia minima a Marte (Km)')
    plt.show()
