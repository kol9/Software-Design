import Foundation

public class LRUCache<Key: Hashable, Value> {
    typealias ValueType = (Key, Value?)
    
    private var cache: [Key: LinkedList<ValueType>.Node] = [:]
    private var orderList: LinkedList<ValueType> = LinkedList()
    private let maxCapacity: Int
    
    public var count: Int {
        cache.count
    }
    
    public init(capacity: Int) {
        precondition(capacity > 0)
        self.maxCapacity = capacity
    }
    
    public subscript(key: Key) -> Value? {
        get {
            guard let node = cache[key] else {
                return nil
            }
            orderList.moveToFront(node)
            return node.value.1
        }
        set(newValue) {
            switch cache[key] {
            case .some(let node):
                orderList.moveToFront(node)
                if let newValue = newValue {
                    node.value = (key, newValue)
                } else {
                    let firstKey = orderList.removeFirst().0
                    cache.removeValue(forKey: firstKey)
                }
            case .none:
                guard let newValue = newValue else {
                    return
                }
                let node = orderList.pushFront((key, newValue))
                cache[key] = node
                node.value = (key, newValue)
            }
            if cache.count > maxCapacity {
                let lastKey = orderList.removeLast().0
                cache.removeValue(forKey: lastKey)
            }
        }
    }
}
