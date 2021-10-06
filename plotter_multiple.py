import sys
import matplotlib.pyplot as plt


if __name__ == '__main__':


    tags = ["out_analytic.txt", "out_beeman.txt", "out_gear.txt", "out_verlet.txt"]    

    times = []
    positions = []
    velocities = []

    for i in range(len(tags)):
        times.append([])
        positions.append([])
        velocities.append([])

        with open(tags[i]) as f:
            even = True
            for line in f:
                if even:
                    times[i].append(float(line))
                else:
                    aux = line.replace(',', '.')
                    values = aux.split()
                    positions[i].append(float(values[0]))
                    velocities[i].append(float(values[1]))
                even = not even

    formats = [ 'r.-', 'g.-', 'b.-', 'k.-']
    labels = ['Analytic Solution', 'Beeman', 'Gear Predictor-Corrector Order 5', 'Verlet']
    
    for i in range(len(tags)):
        plt.plot(times[i], positions[i], formats[i], label=labels[i])

    plt.grid(b=True, which='both', axis='both')
    plt.ylabel('Posición (m)')
    plt.xlabel('Tiempo (s)')
    plt.legend()
    plt.show()
