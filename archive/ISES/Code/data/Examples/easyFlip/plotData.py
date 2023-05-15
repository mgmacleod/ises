# basic parsing and plotting script

import matplotlib.pyplot as plt

modelStats = open('modelStats.txt', 'r')
samplingInterval = 0
numModels = 0

energyVals = []
maxEnergyVals = []
minEnergyVals = []
meanEnergyVals = []
biomassVals = []
meanBiomassVals = []
fitnessVals = []
nGeneVals = []
nSiteVals = []

shapeStats = open('shapeStats.txt', 'r')
numEntriesVals = []
mcShapeVals = []
lcShapeVals = []
highestFreqVals = []
popVals = []
unpopVals = []
puRatioVals = []
meanShapeVals = []
meanFreqVals = []
sdShapeVals = []
sdFreqVals = []


times = []


for line in modelStats:
    if '#' in line:
        tokens = line.split(' ')
        if 'si' in tokens[0]:
            samplingInterval = int(tokens[1])
        else:
            numModels = int(tokens[1])

    else:
        pairs = line.split(';')
        for pair in pairs:
            vals = pair.split('=')
            if vals[0] == 'energy':
                energyVals.append(int(vals[1]))
            elif vals[0] == 'biomass':
                biomassVals.append(int(vals[1]))
            elif vals[0] == 'fitness':
                fitnessVals.append(int(vals[1]))
            elif vals[0] == 'lowestEnergy':
                minEnergyVals.append(int(vals[1]))
            elif vals[0] == 'highestEnergy':
                maxEnergyVals.append(int(vals[1]))
            elif vals[0] == 'meanEnergy':
                meanEnergyVals.append(float(vals[1]))
            elif vals[0] == 'meanBiomass':
                meanBiomassVals.append(float(vals[1]))
            elif vals[0] == 'nGenes':
                nGeneVals.append(int(vals[1]))
            elif vals[0] == 'nSites':
                nSiteVals.append(int(vals[1]))

for line in shapeStats:
    pairs = line.split(';')
    for pair in pairs:
        vals = pair.split('=')
        if vals[0] == 'numEntries':
            numEntriesVals.append(int(vals[1]))
        elif vals[0] == 'mcShape':
            mcShapeVals.append(int(vals[1]))
        elif vals[0] == 'lcShape':
            lcShapeVals.append(int(vals[1]))
        elif vals[0] == 'higestFreq':
            highestFreqVals.append(int(vals[1]))
        elif vals[0] == 'numPopulated':
            popVals.append(int(vals[1]))
        elif vals[0] == 'numUnpopulated':
            unpopVals.append(int(vals[1]))
        elif vals[0] == 'popUnpopRatio':
            puRatioVals.append(float(vals[1]))
        elif vals[0] == 'meanShape':
            meanShapeVals.append(float(vals[1]))
        elif vals[0] == 'meanFreq':
            meanFreqVals.append(float(vals[1]))
        elif vals[0] == 'sdShape':
            sdShapeVals.append(float(vals[1]))
        elif vals[0] == 'sdFreq':
            sdFreqVals.append(float(vals[1]))
                    
count = 0

while count < numModels:
    times.append(count * samplingInterval)
    count = count + 1



plt.plot(times, fitnessVals, 'ro-', label='Fitness')
plt.plot(times, energyVals, 'mh-', label = 'Energy')
plt.plot(times, biomassVals, 'b*-', label = 'Biomass')
plt.legend(loc=0)
plt.xlabel('Generation')
#plt.savefig('fitnessEnergyBiomass.png')

plt.figure()
plt.plot(times, energyVals, 'mh-', label = 'Energy')
plt.legend(loc=0)
plt.xlabel('Generation')
#plt.savefig('energy.png')

plt.figure()
plt.plot(times, nSiteVals, 'yD-', label = '# of binding sites')
plt.plot(times, nGeneVals, 'cx-', label = '# of genes')
plt.legend(loc=0)
plt.xlabel('Generation')
#plt.savefig('nGenesVnSites.png')

plt.figure()
plt.plot(times, numEntriesVals, 'ro-', label='# of entries')
plt.plot(times, popVals, 'b*-', label = '# of populated shapes')
plt.plot(times, mcShapeVals, 'g^:', label = 'most common shape')
plt.legend(loc=0)
plt.xlabel('Generation')
#plt.savefig('shapes1.png')

plt.figure()

plt.plot(times, meanShapeVals, 'ro-', label='mean shape')
plt.plot(times, sdShapeVals, 'mh-', label = 'standard deviation of shapes')
plt.legend(loc=0)
plt.xlabel('Generation')
#plt.savefig('shapes2.png')

plt.figure()
plt.plot(times, puRatioVals, 'ro-', label='populated-unpopulated ratio')
plt.legend(loc=0)
plt.xlabel('Generation')

plt.show()

