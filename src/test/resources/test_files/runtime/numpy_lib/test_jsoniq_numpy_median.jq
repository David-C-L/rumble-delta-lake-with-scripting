(:JIQS: ShouldRun; Output="(2.5, [ 2, 3 ], [ 1.5, 3.5 ], 12, [ 11, 10, 13 ], [ 9, 12, 15 ], 14.5, [ [ [ 0, 1, 2, 3, 4 ], [ 5, 6, 7, 8, 9 ], [ 10, 11, 12, 13, 14 ] ], [ [ 15, 16, 17, 18, 19 ], [ 20, 21, 22, 23, 24 ], [ 25, 26, 27, 28, 29 ] ] ], [ [ [ 7.5, 8.5, 9.5, 10.5, 11.5 ], [ 12.5, 13.5, 14.5, 15.5, 16.5 ], [ 17.5, 18.5, 19.5, 20.5, 21.5 ] ] ], [ [ [ 5, 6, 7, 8, 9 ], [ 20, 21, 22, 23, 24 ] ] ], [ [ [ 2, 7, 12 ], [ 17, 22, 27 ] ] ], 2.5, [ [ [ 1, 2, 3 ], [ 2, 4, 5 ] ] ], [ [ [ 1, 2, 3 ], [ 2, 4, 5 ] ] ], [ [ [ 1.5, 3, 4 ] ] ], [ [ [ 2, 4 ] ] ])":)
import module namespace numpy = "jsoniq_numpy.jq";
numpy:median([[1, 2], [3, 4]]),
numpy:median([[1, 2], [3, 4]], {"axis": 0}),
numpy:median([[1, 2], [3, 4]], {"axis": 1}),
numpy:median([[5, 9, 13], [14, 10, 12], [11, 15, 19]]),
numpy:median([[5, 9, 13], [14, 10, 12], [11, 15, 19]], {"axis": 0}),
numpy:median([[5, 9, 13], [14, 10, 12], [11, 15, 19]], {"axis": 1}),
numpy:median([[[[ 0,  1,  2,  3,  4],[ 5,  6,  7,  8,  9],[10, 11, 12, 13, 14]],[[15, 16, 17, 18, 19],[20, 21, 22, 23, 24],[25, 26, 27, 28, 29]]]]),
numpy:median([[[[ 0,  1,  2,  3,  4],[ 5,  6,  7,  8,  9],[10, 11, 12, 13, 14]],[[15, 16, 17, 18, 19],[20, 21, 22, 23, 24],[25, 26, 27, 28, 29]]]], {"axis": 0}),
numpy:median([[[[ 0,  1,  2,  3,  4],[ 5,  6,  7,  8,  9],[10, 11, 12, 13, 14]],[[15, 16, 17, 18, 19],[20, 21, 22, 23, 24],[25, 26, 27, 28, 29]]]], {"axis": 1}),
numpy:median([[[[ 0,  1,  2,  3,  4],[ 5,  6,  7,  8,  9],[10, 11, 12, 13, 14]],[[15, 16, 17, 18, 19],[20, 21, 22, 23, 24],[25, 26, 27, 28, 29]]]], {"axis": 2}),
numpy:median([[[[ 0,  1,  2,  3,  4],[ 5,  6,  7,  8,  9],[10, 11, 12, 13, 14]],[[15, 16, 17, 18, 19],[20, 21, 22, 23, 24],[25, 26, 27, 28, 29]]]], {"axis": 3}),
numpy:median([[[[1,2,3],[2,4,5]]]]),
numpy:median([[[[1,2,3],[2,4,5]]]], {"axis": 0}),
numpy:median([[[[1,2,3],[2,4,5]]]], {"axis": 1}),
numpy:median([[[[1,2,3],[2,4,5]]]], {"axis": 2}),
numpy:median([[[[1,2,3],[2,4,5]]]], {"axis": 3})