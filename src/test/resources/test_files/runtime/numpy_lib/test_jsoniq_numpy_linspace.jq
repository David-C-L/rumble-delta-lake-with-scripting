(:JIQS: ShouldRun; Output="([ 3, 4.714285714285714, 6.428571428571429, 8.142857142857142, 9.857142857142858, 11.571428571428571, 13.285714285714285, 15 ], [ 3, 4.5, 6, 7.5, 9, 10.5, 12, 13.5 ], [ 3, 4.714285714285714, 6.428571428571429, 8.142857142857142, 9.857142857142858, 11.571428571428571, 13.285714285714285, 15 ], 1.7142857142857142, [ 3, 4.5, 6, 7.5, 9, 10.5, 12, 13.5 ], 1.5, [ 16, 15.857142857142858, 15.714285714285714, 15.571428571428571, 15.428571428571429, 15.285714285714286, 15.142857142857142, 15 ], [ 15, 15, 15, 15, 15 ], 0, [ -15, -7.5, 0, 7.5, 15 ], 7.5, [ -15, -9, -3, 3, 9 ], 6, [ -127, -120.95652173913044, -114.91304347826087, -108.86956521739131, -102.82608695652173, -96.78260869565217, -90.73913043478261, -84.69565217391303, -78.65217391304347, -72.6086956521739, -66.56521739130434, -60.52173913043478, -54.47826086956522, -48.434782608695656, -42.39130434782608, -36.347826086956516, -30.304347826086953, -24.26086956521739, -18.217391304347828, -12.173913043478251, -6.130434782608688, -0.08695652173912549, 5.956521739130437, 12, 18.043478260869563, 24.086956521739125, 30.13043478260869, 36.17391304347828, 42.21739130434784, 48.260869565217405, 54.30434782608697, 60.34782608695653, 66.3913043478261, 72.43478260869566, 78.47826086956522, 84.52173913043478, 90.56521739130434, 96.6086956521739, 102.6521739130435, 108.69565217391306, 114.73913043478262, 120.78260869565219, 126.82608695652175, 132.8695652173913, 138.91304347826087, 144.95653 ], 6.043478260869565, [ -30, -27.857142857142858, -25.714285714285715, -23.57142857142857, -21.42857142857143, -19.285714285714285, -17.142857142857142, -15 ])":)
import module namespace numpy = "jsoniq_numpy.jq";
numpy:linspace(3, 15, {"num": 8, "endpoint": true, "retstep": false}), numpy:linspace(3, 15, {"num": 8, "endpoint": false, "retstep": false}), numpy:linspace(3, 15, {"num": 8, "endpoint": true, "retstep": true}), numpy:linspace(3, 15, {"num": 8, "endpoint": false, "retstep": true}), numpy:linspace(16, 15, {"num": 8, "endpoint": true, "retstep": false}), numpy:linspace(15, 15, {"num": 5, "endpoint": true, "retstep": true}), numpy:linspace(-15, 15, {"num": 5, "endpoint": true, "retstep": true}), numpy:linspace(-15, 15, {"num": 5, "endpoint": false, "retstep": true}), numpy:linspace(-127, 151, {"num": 46, "endpoint": false, "retstep": true}), numpy:linspace(-30, -15, {"num": 8, "endpoint": true, "retstep": false})