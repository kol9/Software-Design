import Foundation

public protocol Clock {
    var now: TimeInterval { get }
}
