function change(amount)
  if math.type(amount) ~= "integer" then
    error("Amount must be an integer")
  end
  if amount < 0 then
    error("Amount cannot be negative")
  end
  local counts, remaining = {}, amount
  for _, denomination in ipairs({25, 10, 5, 1}) do
    counts[denomination] = remaining // denomination
    remaining = remaining % denomination
  end
  return counts
end

-- Write your first then lower case function here
function first_then_lower_case(strings, predicate)
  for _, value in pairs(strings) do
    if predicate(value) == true
    then
      return string.lower(value)
    end

  end

  return nil
end

-- Write your powers generator here
function powers_generator(base, limit)
  return coroutine.create(function ()
    local exp = 0
    while true do
      local num = base ^ exp
      if num > limit then
        break
      else
        coroutine.yield(num)
      end
        exp = exp + 1
    end
  end)
end

-- Write your say function here
function say(str)
  print("\n")
  
  local words = {}

  if str ~= nil then
    table.insert(words, str)  
  end

  if str == nil and #words == 0 then
    return ""
  end

  function addWord(newStr)
    if newStr == nil then
      return table.concat(words, " ")
    else
      table.insert(words, newStr)
      return addWord
    end
  end

  return addWord
end

-- Write your line count function here
function meaningful_line_count(filename)
  local file = io.open(filename, 'r')
  local line_count = 0

  if file then
    for line in file:lines() do
      local str = line:match("^%s*(%S)")
      if not(str == nil or str:sub(1,1) == '#') then
        line_count = line_count + 1
      end
    end
    file:close()
    
  else
    error("No such file")
  end
    
  return line_count
end


-- Write your Quaternion table here
Quaternion = {}

quatmeta = {
  __index = {
    coefficients = function(self)
      return {self.a, self.b, self.c, self.d}
    end,

    conjugate = function(self)
      print(Quaternion.new(self.a, -self.b, -self.c, -self.d))
      return Quaternion.new(self.a, -self.b, -self.c, -self.d)
    end
  },
  __add = function(self, other)
    return Quaternion.new(self.a + other.a, self.b + other.b, self.c + other.c, self.d + other.d)
  end,
  __mul = function(self, other)
    return Quaternion.new(self.a * other.a - self.b * other.b - self.c * other.c - self.d * other.d,
                          self.a * other.b + self.b * other.a + self.c * other.d - self.d * other.c,
                          self.a * other.c - self.b * other.d + self.c * other.a + self.d * other.b,
                          self.a * other.d + self.b * other.c - self.c * other.b + self.d * other.a)
  end,
  __tostring = function(self)
    local final_string = ""

    -- Edge Case where everything is 0
    if self.a == 0 and self.b == 0 and self.c == 0 and self.d == 0 then
        return "0"
    end

    -- First coefficient
    if self.a ~= 0 then
        final_string = final_string .. tostring(self.a)
    end

    -- Second coefficient (i)
    if self.b ~= 0 then
        if final_string == "" and math.abs(self.b) ~= 1 then
            final_string = final_string .. tostring(self.b) .. 'i'
        elseif final_string == "" and self.b == 1 then
            final_string = final_string .. 'i'
        elseif final_string == "" and self.b == -1 then
            final_string = final_string .. '-i'
        else
            if self.b > 0 then
                final_string = final_string .. "+"
            else
                final_string = final_string .. "-"
            end

            if math.abs(self.b) ~= 1 then
                final_string = final_string .. tostring(math.abs(self.b)) .. 'i'
            else
                final_string = final_string .. 'i'
            end
        end
    end

    -- Third coefficient (j)
    if self.c ~= 0 then
        if final_string == "" and math.abs(self.c) ~= 1 then
            final_string = final_string .. tostring(self.c) .. 'j'
        elseif final_string == "" and self.c == 1 then
            final_string = final_string .. 'j'
        elseif final_string == "" and self.c == -1 then
            final_string = final_string .. '-j'
        else
            if self.c > 0 then
                final_string = final_string .. "+"
            else
                final_string = final_string .. "-"
            end

            if math.abs(self.c) ~= 1 then
                final_string = final_string .. tostring(math.abs(self.c)) .. 'j'
            else
                final_string = final_string .. 'j'
            end
        end
    end

    -- Fourth coefficient (k)
    if self.d ~= 0 then
        if final_string == "" and math.abs(self.d) ~= 1 then
            final_string = final_string .. tostring(self.d) .. 'k'
        elseif final_string == "" and self.d == 1 then
            final_string = final_string .. 'k'
        elseif final_string == "" and self.d == -1 then
            final_string = final_string .. '-k'
        else
            if self.d > 0 then
                final_string = final_string .. "+"
            else
                final_string = final_string .. "-"
            end

            if math.abs(self.d) ~= 1 then
                final_string = final_string .. tostring(math.abs(self.d)) .. 'k'
            else
                final_string = final_string .. 'k'
            end
        end
    end

    return final_string
  end,

  __eq = function(self, other)
    return self.a == other.a and self.b == other.b and self.c == other.c and self.d == other.d
  end
}

Quaternion.new = function(a, b, c, d)
  return setmetatable({a = a, b = b, c = c, d = d}, quatmeta)
end


