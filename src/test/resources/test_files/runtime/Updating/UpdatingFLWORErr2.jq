(:JIQS: ShouldCrash; ErrorCode="XUST0001"; ErrorMetadata="LINE:5:COLUMN:8:" :)
copy json $je := for $i in (1 to 4)
                 return [$i mod 2]
modify
    for $l in replace json value of [2,2,3][[1]] with 1
    return
        replace json value of $l[[1]] with 1
return $je

(: expr in for clause is not simple :)