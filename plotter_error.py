import sys
import matplotlib.pyplot as plt


if __name__ == '__main__':


    tags = ["out_beeman_error.txt", "out_gear_error.txt", "out_verlet_error.txt"]    

    times = []
    errors = []

    for i in range(len(tags)):
        times.append([])
        errors.append([])

        with open(tags[i]) as f:
            even = True
            for line in f:
                if even:
                    times[i].append(float(line))
                else:
                    errors[i].append(float(line))
                even = not even

    formats = [ 'g.-', 'b.-', 'k.-']
    labels = ['Beeman ECM', 'Gear Predictor-Corrector Order 5 ECM', 'Verlet ECM']
    
    for i in range(len(tags)):
        plt.plot(times[i], errors[i], formats[i], label=labels[i])

    plt.grid(b=True, which='both', axis='both')
    plt.ylabel('ECM')
    plt.xlabel('Tiempo (s)')
    plt.yscale('log')
    plt.legend()
    plt.show()
