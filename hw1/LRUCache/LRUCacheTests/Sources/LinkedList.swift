import Foundation

class LinkedList<Value> {
    class Node {
        var value: Value
        var next: Node?
        weak var prev: Node?
        
        init(value: Value) {
            self.value = value
        }
    }
    
    private(set) var count: Int = 0
    private var head: Node?
    private var tail: Node?
    
    var isEmpty: Bool {
        count == 0
    }
    
    func pushFront(_ value: Value) -> Node {
        let newNode = Node(value: value)
        
        if let headNode = head {
            newNode.next = headNode
            headNode.prev = newNode
        } else {
            tail = newNode
        }
        head = newNode
        count += 1
        return newNode
    }
    
    func moveToFront(_ node: Node) {
        if node === head {
            return
        }
        let previous = node.prev
        let next = node.next
        previous?.next = next
        next?.prev = previous
        
        node.next = head
        node.prev = nil
        
        if node === tail {
            tail = previous
        }
        
        head?.prev = node
        head = node
    }
    
    func removeLast() -> Value {
        precondition(!isEmpty)
        
        let lastValue = tail!.value
        
        if let preLast = tail!.prev {
            preLast.next = nil
        }
        tail = tail!.prev
        return lastValue
    }
    
    func removeFirst() -> Value {
        precondition(!isEmpty)
        
        let firstValue = head!.value
        
        if let second = head!.next {
            second.prev = nil
        }
        head = head?.next
        return firstValue
    }
}

extension LinkedList: CustomStringConvertible {
    var description: String {
        var str = "["
        var curNode = head
        
        while curNode != nil {
            str += "\(curNode!.value)"
            curNode = curNode!.next
            if curNode != nil {
                str += ", "
            }
        }
        return "\(str)]"
    }
}
