(:JIQS: ShouldCrash; ErrorCode="XUST0001"; ErrorMetadata="LINE:3:COLUMN:7:" :)
copy $je := {"foo": "bar", "foobar": "barfoo"}
modify replace value of (replace value of $je.foo with "foo").foo with "foo"
return $je

(: target expr is not simple :)