require "luarocks.loader"
local threads = require "threads"

function calculateFactorial(n)
    local pool = threads.Threads() -- By default, it uses the number of available CPU cores

    for i = 2, n do
        pool:addjob(function()
            local partialResult = 1
            for j = 2, i do
                partialResult = partialResult * j
            end
            return partialResult
        end,
        function(partialResult)
            -- Process the result if needed
        end)
    end

    pool:synchronize()

    local result = 1
    for i = 1, pool:size() do
        result = result * pool:receive()
    end

    return result
end

local n = 100 -- The number for which factorial needs to be calculated
local factorial = calculateFactorial(n)
print("Factorial of", n, "is:", factorial)