(:JIQS: ShouldRun; Output="(123456792.4123456789, 123456793.4123456789, 123456794.4123456789, 123456795.4123456789, 123456796.4123456789, 123456797.4123456789, 123456798.4123456789, 123456799.4123456789, 123456800.4123456789, 123456801.4123456789, 123456802.4123456789, 123456803.4123456789, 123456804.4123456789, 123456805.4123456789, 123456806.4123456789, 123456807.4123456789, 123456808.4123456789, 123456809.4123456789, 123456810.4123456789, 123456811.4123456789, 123456812.4123456789, 123456813.4123456789, 123456814.4123456789, 123456815.4123456789, 123456816.4123456789, 123456817.4123456789, 123456818.4123456789, 123456819.4123456789, 123456820.4123456789, 123456821.4123456789, 123456822.4123456789, 123456823.4123456789, 123456824.4123456789, 123456825.4123456789, 123456826.4123456789, 123456827.4123456789, 123456828.4123456789, 123456829.4123456789, 123456830.4123456789, 123456831.4123456789, 123456832.4123456789, 123456833.4123456789, 123456834.4123456789, 123456835.4123456789, 123456836.4123456789, 123456837.4123456789, 123456838.4123456789, 123456839.4123456789, 123456840.4123456789, 123456841.4123456789, 123456842.4123456789, 123456843.4123456789, 123456844.4123456789, 123456845.4123456789, 123456846.4123456789, 123456847.4123456789, 123456848.4123456789, 123456849.4123456789, 123456850.4123456789, 123456851.4123456789, 123456852.4123456789, 123456853.4123456789, 123456854.4123456789, 123456855.4123456789, 123456856.4123456789, 123456857.4123456789, 123456858.4123456789, 123456859.4123456789, 123456860.4123456789, 123456861.4123456789, 123456862.4123456789, 123456863.4123456789, 123456864.4123456789, 123456865.4123456789, 123456866.4123456789, 123456867.4123456789, 123456868.4123456789, 123456869.4123456789, 123456870.4123456789, 123456871.4123456789, 123456872.4123456789, 123456873.4123456789, 123456874.4123456789, 123456875.4123456789, 123456876.4123456789, 123456877.4123456789, 123456878.4123456789, 123456879.4123456789, 123456880.4123456789, 123456881.4123456789, 123456882.4123456789, 123456883.4123456789, 123456884.4123456789, 123456885.4123456789, 123456886.4123456789, 123456887.4123456789, 123456888.4123456789, 123456889.4123456789, 123456890.4123456789, 123456891.4123456789)" :)
for $i in parallelize((1 to 100) ! ($$ cast as decimal), 10)
let $a := decimal(2.4)
let $b := 123456789.0123456789
let $j := $i + $a + $b
return $j