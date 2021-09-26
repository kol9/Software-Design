import XCTest

class LRUCacheTests: XCTestCase {
    
    // MARK: - Test cases
    
    func testSimpleAddAndRemove() {
        
        // Given
        let cache = LRUCache<Int, [Int]>(capacity: 5)
        let arr = [1, 2, 3, 4, 5]
        
        // When
        cache[100] = arr
        
        // Then
        XCTAssert(cache.count == 1)
        XCTAssert(cache[100] == arr)
        
        // When
        cache[100] = nil
        
        // Then
        XCTAssert(cache.count == 0)
        XCTAssert(cache[100] == nil)
    }
    
    func testOverwriteValueForKey() {
        
        // Given
        let cache = LRUCache<Int, String>(capacity: 5)
        
        // When
        cache[100] = "One hundred"
        cache[100] = "One"
        cache[100] = "one"
        cache[100] = "1 hundred"
        
        // Then
        XCTAssert(cache.count == 1)
        XCTAssert(cache[100] == "1 hundred")
    }
    
    func testCapacityOverflow() {
        
        // Given
        let cache = LRUCache<Int, Int>(capacity: 3)
        
        // When
        cache[1] = 1
        cache[2] = 2
        cache[3] = 3
        cache[4] = 4
        
        // Then
        XCTAssert(cache.count == 3)
        XCTAssert(cache[1] == nil)
        XCTAssert(cache[2] != nil)
        XCTAssert(cache[3] != nil)
        XCTAssert(cache[4] != nil)
    }
    
    func testKeyTypeString() {
        
        // Given
        let cache = LRUCache<String, String>(capacity: 5)
        
        // When
        cache["A"] = "A"
        cache["a"] = "a"
        cache["b"] = "b"
        cache["c"] = "c"
        
        // Then
        XCTAssert(cache.count == 4)
        XCTAssert(cache["A"] == "A")
        XCTAssert(cache["a"] == "a")
        XCTAssert(cache["b"] == "b")
        XCTAssert(cache["c"] == "c")
    }
    
    func testRandomQueriesSequences() {
        testRandomQuery(withSize: 5, cacheCapacity: 5)
        testRandomQuery(withSize: 20, cacheCapacity: 5)
        testRandomQuery(withSize: 100, cacheCapacity: 1)
        testRandomQuery(withSize: 1000, cacheCapacity: 2)
        testRandomQuery(withSize: 1000, cacheCapacity: 3)
        testRandomQuery(withSize: 1000, cacheCapacity: 50)
        testRandomQuery(withSize: 1000, cacheCapacity: 100)
        testRandomQuery(withSize: 1000, cacheCapacity: 250)
        testRandomQuery(withSize: 1000, cacheCapacity: 900)
        testRandomQuery(withSize: 1000, cacheCapacity: 999)
    }
    
    // MARK: - Helpers
    
    private func testRandomQuery(withSize size: Int, cacheCapacity: Int) {
        
        // Given
        let cache = LRUCache<Int, Int>(capacity: cacheCapacity)
        let (randomArr, randomDict) = generateQuery(ofSize: size)
        
        // When
        randomArr.traverseWithWindow(withSize: cacheCapacity) { element, lIndex, rIndex in
            cache[element] = randomDict[element]
            
            // Then
            /// Each item before current window should be erased from cache,
            /// because of the capacity
            for i in (0..<lIndex) {
                let value = cache[randomArr[i]]
                XCTAssert(value == nil)
            }
            
            /// Each item inside current window should be present in cache
            for i in (lIndex...rIndex) {
                let value = cache[randomArr[i]]
                XCTAssert(randomDict[randomArr[i]] == value)
            }
        }
    }
    
    private func generateQuery(ofSize size: Int) -> ([Int], [Int: Int]) {
        let randomKeys: [Int] = Array(1...size).shuffled()
        let randomValues = (1...size).map { _ in Int.random(in: 1...size) }
        let randomDict = Dictionary(uniqueKeysWithValues: zip(randomKeys, randomValues))
        return (randomKeys, randomDict)
    }
}

extension Array {
    
    func traverseWithWindow(
        withSize size: Int,
        _ closure: ((_ element: Element, _ l: Int, _ r: Int) -> Void)
    ) {
        for i in 0..<count {
            let j = Swift.max(0, i - size + 1)
            closure(self[i], j, i)
        }
    }
}
