import Foundation

public protocol EventStatisticProtocol {
    func incEvent(name: String)
    func getEventStatisticBy(name: String) -> Double
    func getAllEventStatistic() -> [String: Double]
    func printStatistic()
}
