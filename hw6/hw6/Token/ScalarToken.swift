//
//  ScalarToken.swift
//  hw6
//
//  Created by Nikolay Yarlychenko on 05.12.2021.
//

import Foundation

public enum ScalarToken: ArithToken {
    public func accept(_ visitor: TokenVisitor) {
        visitor.visit(self)
    }
        
    public var value: Int {
        switch self {
        case .intValue(let v):
            return v
        }
    }
    
    case intValue(v: Int)
}
