(:JIQS: ShouldCrash; ErrorCode="XUST0001"; ErrorMetadata="LINE:2:COLUMN:0:" :)
copy $je := rename {"bar" : 1}.bar as "bar"
modify rename $je.foo as "bar"
return $je

(: source expr is not simple :)