package tv.wallberg

def name = "AA"

static def prepend (Integer it) {
    assert it >= 0  // only positive values accepted 
    if (it < 10)
        '0' + it
    else
        it.toString()
  }

assert "01" == prepend(1)
assert "11" == prepend(11)
shouldfail(prepend(-1))

